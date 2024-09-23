package com.Mindhubcohort55.Homebanking.services;

import com.Mindhubcohort55.Homebanking.dtos.AccountDto;
import com.Mindhubcohort55.Homebanking.models.Account;
import com.Mindhubcohort55.Homebanking.models.Client;
import com.Mindhubcohort55.Homebanking.models.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    List<AccountDto> getAllAccountsDto(List<Account> accounts);
    Account getAccountById(Long id);
    ResponseEntity<?> getAccountDto(Long id);
    Boolean accountNumberValidator(Authentication authentication);
    Account makeNewAccount();
    void saveAccountRepository(Account account);
    ResponseEntity<?> newAccountForClient(Authentication authentication);
    List<Account> getAccountsByOwner(Authentication authentication);
    Account getAccountByNumber(String accountNumber);
    void addTransactionToAccount(Account account, Transaction transaction);
    ResponseEntity<?> accountRequestValidator(Authentication authentication);

}
