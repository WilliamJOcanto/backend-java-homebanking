package com.Mindhubcohort55.Homebanking.dtos;

import com.Mindhubcohort55.Homebanking.models.Card;
import com.Mindhubcohort55.Homebanking.models.CardColor;
import com.Mindhubcohort55.Homebanking.models.CardType;
import java.time.LocalDate;

public class CardDto {

    private long id;
    private String cardHolder;
    private CardType Cardtype;
    private CardColor cardColor;
    private String cardNumber;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;

    public CardDto(Card card){
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.Cardtype = card.getCardType();
        this.cardNumber = card.getCardNumber();
        this.cardColor = card.getCardColor();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public CardType getCardtype() {
        return Cardtype;
    }

    public CardColor getCardColor() {
        return cardColor;
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

    public LocalDate getThruDate() {
        return thruDate;
    }
}
