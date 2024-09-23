package com.Mindhubcohort55.Homebanking.services;

import com.Mindhubcohort55.Homebanking.dtos.MakeTransactionDto;
import com.Mindhubcohort55.Homebanking.models.Account;
import com.Mindhubcohort55.Homebanking.models.Client;
import com.Mindhubcohort55.Homebanking.models.Transaction;
import com.Mindhubcohort55.Homebanking.models.TransactionType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface TransactionService {
    void saveTransaction(Transaction transaction);
    Boolean accountFieldValidator(String accountField);
    Boolean amountTransactionFieldValidator(MakeTransactionDto makeTransactionDto);
    Boolean descriptionFieldValidator(MakeTransactionDto makeTransactionDto);
    Boolean isSourceAndDestinationEquals(MakeTransactionDto makeTransactionDto);
    Boolean isAccountExist(String accountNumber);
    Boolean isAccountBelongsToClient(Account account, Client client);
    Boolean enoughBalanceValidator(Account account, MakeTransactionDto makeTransactionDto);
    Transaction createTransaction(TransactionType transactionType, double amount, String description, String descriptionComplement);
    void setAccountBalance(Account account, double amount);
    ResponseEntity<?> transactionRequestValidator(MakeTransactionDto makeTransactionDto, Account account, Client client);
    ResponseEntity<?> generateTransaction(Authentication authentication, MakeTransactionDto makeTransactionDto);
}
