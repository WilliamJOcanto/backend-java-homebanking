package com.Mindhubcohort55.Homebanking.dtos;

public record MakeTransactionDto(String sourceAccount, String destinationAccount, Double amount, String description) {
}
