package com.Mindhubcohort55.Homebanking.controllers;

import com.Mindhubcohort55.Homebanking.dtos.AccountDto;
import com.Mindhubcohort55.Homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public ResponseEntity<List<AccountDto>> getAllAccounts(){
        return new ResponseEntity<>(accountService.getAllAccountsDto(accountService.getAllAccounts()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id){
        return accountService.getAccountDto(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<?> createAccount(Authentication authentication){
        try{
        return accountService.newAccountForClient(authentication);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error creating account" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/clients/current/accounts")
    public ResponseEntity<?> getClientAccounts(Authentication authentication){
        return new ResponseEntity<>(accountService.getAllAccountsDto(accountService.getAccountsByOwner(authentication)), HttpStatus.OK );
    }
}
