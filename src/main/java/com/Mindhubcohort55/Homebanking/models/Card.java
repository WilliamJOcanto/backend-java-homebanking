package com.Mindhubcohort55.Homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cardHolder;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private CardColor cardColor;

    private String cardNumber;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Client owner;

    public Card() {
    }

    public Card(CardType cardType, CardColor cardColor, String cardNumber, String cvv, LocalDate fromDate, LocalDate thruDate, Client client) {
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Client getClientOwner() {
        return owner;
    }

    public void setClientOwner(Client clientOwner) {
        this.owner = clientOwner;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardHolder='" + cardHolder + '\'' +
                ", cardType=" + cardType +
                ", cardColor=" + cardColor +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", fromDate=" + fromDate +
                ", thruDate=" + thruDate +
                '}';
    }
}
