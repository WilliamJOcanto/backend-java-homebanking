package com.Mindhubcohort55.Homebanking.services.impl;

import com.Mindhubcohort55.Homebanking.dtos.AccountDto;
import com.Mindhubcohort55.Homebanking.models.Account;
import com.Mindhubcohort55.Homebanking.models.Transaction;
import com.Mindhubcohort55.Homebanking.repositories.AccountRepository;
import com.Mindhubcohort55.Homebanking.services.AccountService;
import com.Mindhubcohort55.Homebanking.services.ClientService;
import com.Mindhubcohort55.Homebanking.utils.AccountNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountNumberGenerator accountNumberGenerator;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDto> getAllAccountsDto(List<Account> accounts) {
        return accounts.stream().map(AccountDto::new).collect(Collectors.toList());
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> getAccountDto(Long id) {
        Account account = getAccountById(id);

        if(account == null){
            return new ResponseEntity<>("Account not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new AccountDto((account)), HttpStatus.OK);
    }

    @Override
    public Boolean accountNumberValidator(Authentication authentication) {
        return clientService.getClientByEmail(authentication).getAccounts().size() == 3;
    }

    @Override
    public Account makeNewAccount() {
        return new Account(accountNumberGenerator.makeAccountNumber(), LocalDateTime.now(), 00.0);
    }

    @Override
    public void saveAccountRepository(Account account) {
        accountRepository.save(account);
    }

    @Override
    public ResponseEntity<?> accountRequestValidator(Authentication authentication) {
        if(accountNumberValidator(authentication)){
            return new ResponseEntity<>("You cannot create a new account at this time. You have reached the maximum number of allowed accounts (3)", HttpStatus.FORBIDDEN);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> newAccountForClient(Authentication authentication) {

        ResponseEntity<?> validatorResponse = accountRequestValidator(authentication);

        if(validatorResponse != null){
           return validatorResponse;
        }
        Account newAccount = makeNewAccount();
        clientService.addAccountToClient(newAccount, authentication);
        saveAccountRepository(newAccount);

        clientService.saveClientRepository(authentication);

        return new ResponseEntity<>("Account created", HttpStatus.CREATED);
    }

    @Override
    public List<Account> getAccountsByOwner(Authentication authentication) {
        return accountRepository.findByOwner(clientService.getClientByEmail(authentication));
    }

    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByNumber(accountNumber);
    }

    @Override
    public void addTransactionToAccount(Account account, Transaction transaction) {
        account.addTransaction(transaction);
    }
}
