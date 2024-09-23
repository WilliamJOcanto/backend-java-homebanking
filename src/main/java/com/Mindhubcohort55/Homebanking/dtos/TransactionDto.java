package com.Mindhubcohort55.Homebanking.dtos;

import com.Mindhubcohort55.Homebanking.models.Transaction;
import com.Mindhubcohort55.Homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDto {

    private long id;
    private TransactionType transactionType;
    private double amount;
    private String description;
    private LocalDateTime dateTransaction;

    public TransactionDto(Transaction transaction) {
        this.id = transaction.getId();
        this.transactionType = transaction.getTransactionType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.dateTransaction = transaction.getDateTransaction();
    }
    public Long getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }
}
