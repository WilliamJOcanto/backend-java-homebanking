package com.Mindhubcohort55.Homebanking.dtos;

import com.Mindhubcohort55.Homebanking.models.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<AccountDto> accounts = new ArrayList<>();
    private List<ClientLoanDto> loans = new ArrayList<>();
    private Set<CardDto> cards;

    public ClientDto (Client client){
        this.id = client.getId();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(AccountDto::new).collect(Collectors.toList());
        this.loans = client.getClientLoans().stream().map(ClientLoanDto::new).collect(Collectors.toList());
        this.cards = client.getCards().stream().map(CardDto::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountDto> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDto> getLoans() {
        return loans;
    }

    public Set<CardDto> getCards() {
        return cards;
    }
}
