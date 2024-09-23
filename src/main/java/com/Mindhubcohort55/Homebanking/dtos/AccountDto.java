package com.Mindhubcohort55.Homebanking.dtos;

import com.Mindhubcohort55.Homebanking.models.Account;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDto {

    private Long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private List<TransactionDto> transactions = new ArrayList<>();

    public AccountDto(Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public List<TransactionDto> getTransactions() {
        return transactions;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
