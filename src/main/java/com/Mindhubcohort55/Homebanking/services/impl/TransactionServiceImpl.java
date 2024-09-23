package com.Mindhubcohort55.Homebanking.services.impl;

import com.Mindhubcohort55.Homebanking.dtos.MakeTransactionDto;
import com.Mindhubcohort55.Homebanking.models.Account;
import com.Mindhubcohort55.Homebanking.models.Client;
import com.Mindhubcohort55.Homebanking.models.Transaction;
import com.Mindhubcohort55.Homebanking.models.TransactionType;
import com.Mindhubcohort55.Homebanking.repositories.AccountRepository;
import com.Mindhubcohort55.Homebanking.repositories.TransactionRepository;
import com.Mindhubcohort55.Homebanking.services.AccountService;
import com.Mindhubcohort55.Homebanking.services.ClientService;
import com.Mindhubcohort55.Homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Boolean accountFieldValidator(String accountField) {
        return accountField.isBlank();
    }

    @Override
    public Boolean amountTransactionFieldValidator(MakeTransactionDto makeTransactionDto) {
        return makeTransactionDto.amount() == null || makeTransactionDto.amount().isNaN() || makeTransactionDto.amount() <= 0;
    }

    @Override
    public Boolean descriptionFieldValidator(MakeTransactionDto makeTransactionDto) {
        return makeTransactionDto.description().isBlank();
    }

    @Override
    public Boolean isSourceAndDestinationEquals(MakeTransactionDto makeTransactionDto) {
        return makeTransactionDto.sourceAccount().equals(makeTransactionDto.destinationAccount());
    }

    @Override
    public Boolean isAccountExist(String accountNumber) {
        return accountRepository.existsByNumber(accountNumber);
    }

    @Override
    public Boolean isAccountBelongsToClient(Account account, Client client) {
        return accountRepository.existsByIdAndOwner(account.getId(), client);
    }

    @Override
    public Boolean enoughBalanceValidator(Account account, MakeTransactionDto makeTransactionDto) {
        return account.getBalance() < makeTransactionDto.amount();
    }

    @Override
    public Transaction createTransaction(TransactionType transactionType, double amount, String description, String descriptionComplement) {
        return new Transaction(transactionType, amount, description + " " + descriptionComplement, LocalDateTime.now());
    }

    @Override
    public void setAccountBalance(Account account, double amount) {
        double accountBalance = account.getBalance();
        account.setBalance(accountBalance + amount);
    }

    @Override
    public ResponseEntity<?> transactionRequestValidator(MakeTransactionDto makeTransactionDto, Account account, Client client) {

        if(accountFieldValidator(makeTransactionDto.sourceAccount())){
            return new ResponseEntity<>("The source account field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(accountFieldValidator(makeTransactionDto.destinationAccount())){
            return new ResponseEntity<>("The destination account field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(amountTransactionFieldValidator(makeTransactionDto)){
            return new ResponseEntity<>("Enter a valid amount and greater than zero", HttpStatus.FORBIDDEN);
        }
        if(descriptionFieldValidator(makeTransactionDto)){
            return new ResponseEntity<>("The description field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(isSourceAndDestinationEquals(makeTransactionDto)){
            return new ResponseEntity<>("The source account and the destination account must not be the same", HttpStatus.FORBIDDEN);
        }
        if(!isAccountExist(makeTransactionDto.sourceAccount())){
            return new ResponseEntity<>("The source account entered does not exist", HttpStatus.FORBIDDEN);
        }
        if(!isAccountBelongsToClient(account, client)){
            return new ResponseEntity<>("The source account entered does not belong to the client", HttpStatus.FORBIDDEN);
        }
        if(!isAccountExist(makeTransactionDto.destinationAccount())){
            return new ResponseEntity<>("The destination account entered does not exist", HttpStatus.FORBIDDEN);
        }
        if(enoughBalanceValidator(account, makeTransactionDto)){
            return new ResponseEntity<>("The selected account does not have sufficient balance to carry out the operation", HttpStatus.FORBIDDEN);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> generateTransaction(Authentication authentication, MakeTransactionDto makeTransactionDto) {

        Client client = clientService.getClientByEmail(authentication);
        Account sourceAccount = accountService.getAccountByNumber(makeTransactionDto.sourceAccount());
        Account destinationAccount = accountService.getAccountByNumber(makeTransactionDto.destinationAccount());

        ResponseEntity<?> validatorResponse = transactionRequestValidator(makeTransactionDto, sourceAccount, client);
        if(validatorResponse != null){
            return validatorResponse;
        }
        Transaction sourceTransaction = createTransaction(TransactionType.DEBIT, -makeTransactionDto.amount(), makeTransactionDto.description(), makeTransactionDto.destinationAccount());
        accountService.addTransactionToAccount(sourceAccount, sourceTransaction);
        saveTransaction(sourceTransaction);

        Transaction destinyTransaction = createTransaction(TransactionType.CREDIT, makeTransactionDto.amount(), makeTransactionDto.description(), makeTransactionDto.sourceAccount());
        accountService.addTransactionToAccount(destinationAccount, destinyTransaction);
        saveTransaction(destinyTransaction);

        setAccountBalance(sourceAccount, -makeTransactionDto.amount());
        accountService.saveAccountRepository(sourceAccount);

        setAccountBalance(destinationAccount, makeTransactionDto.amount());
        accountService.saveAccountRepository(destinationAccount);

        return new ResponseEntity<>("Transaction completed successfully", HttpStatus.CREATED);
    }
}
