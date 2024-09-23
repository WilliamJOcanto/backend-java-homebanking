package com.Mindhubcohort55.Homebanking.controllers;

import com.Mindhubcohort55.Homebanking.dtos.ApplyLoanDto;
import com.Mindhubcohort55.Homebanking.services.LoanService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Transactional
     @PostMapping("/loans")
     public ResponseEntity<?> applyLoan(@RequestBody ApplyLoanDto applyLoanDto, Authentication authentication){
        try{
            return loanService.newLoanForClient(authentication, applyLoanDto);
        }
        catch (Exception e){
            return new ResponseEntity<>("Error in loan application: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
     }

    @GetMapping("/loans")
    public ResponseEntity<?> getAllLoans(Authentication authentication){
        return loanService.getAllAvailableLoanDto(authentication);
    }
}

