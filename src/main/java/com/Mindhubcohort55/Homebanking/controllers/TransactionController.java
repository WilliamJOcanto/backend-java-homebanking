package com.Mindhubcohort55.Homebanking.controllers;

import com.Mindhubcohort55.Homebanking.dtos.MakeTransactionDto;

import com.Mindhubcohort55.Homebanking.services.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<?> makeTransaction(@RequestBody MakeTransactionDto makeTransactionDto, Authentication authentication){

        try{
            return transactionService.generateTransaction(authentication, makeTransactionDto);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error making transaction: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
