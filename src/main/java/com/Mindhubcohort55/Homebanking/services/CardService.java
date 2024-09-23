package com.Mindhubcohort55.Homebanking.services;

import com.Mindhubcohort55.Homebanking.dtos.CardDto;
import com.Mindhubcohort55.Homebanking.dtos.CreateCardDto;
import com.Mindhubcohort55.Homebanking.models.Card;
import com.Mindhubcohort55.Homebanking.models.CardType;
import com.Mindhubcohort55.Homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Set;

public interface CardService {
    Set<Card> getCardByType(Client client, CardType cardType);
    Boolean allCardsNumberValidator(Client client);
    Boolean cardsByTypeValidator(Set<Card> cards, CreateCardDto createCardDto);
    Boolean cardsByColorValidator(Card card, CreateCardDto createCardDto);
    Card makeNewCard(Client client, CreateCardDto createCardDto);
    void saveCardRepository(Card card);
    ResponseEntity<?> newCardForClient(Authentication authentication, CreateCardDto createCardDto);
    Set<Card> getAllCards(Client client);
    Set<CardDto> getCardsDto(Set<Card> card);
    Boolean isCardTypeEmpty(CreateCardDto createCardDto);
    Boolean isCardMembership(CreateCardDto createCardDto);
    ResponseEntity<?> cardRequestValidator(Client client, CreateCardDto createCardDto);
}
