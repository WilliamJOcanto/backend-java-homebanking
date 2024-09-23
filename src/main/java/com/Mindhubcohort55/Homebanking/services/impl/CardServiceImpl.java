package com.Mindhubcohort55.Homebanking.services.impl;

import com.Mindhubcohort55.Homebanking.dtos.CardDto;
import com.Mindhubcohort55.Homebanking.dtos.CreateCardDto;
import com.Mindhubcohort55.Homebanking.models.Card;
import com.Mindhubcohort55.Homebanking.models.CardColor;
import com.Mindhubcohort55.Homebanking.models.CardType;
import com.Mindhubcohort55.Homebanking.models.Client;
import com.Mindhubcohort55.Homebanking.repositories.CardRepository;
import com.Mindhubcohort55.Homebanking.services.CardService;
import com.Mindhubcohort55.Homebanking.services.ClientService;
import com.Mindhubcohort55.Homebanking.utils.CardNumberGenerator;
import com.Mindhubcohort55.Homebanking.utils.CvvGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Set<Card> getCardByType(Client client, CardType cardType) {
        return client.getCards().stream().filter(card -> card.getCardType() == cardType).collect(Collectors.toSet());
    }

    @Override
    public Boolean allCardsNumberValidator(Client client) {
        return client.getCards().size() == 6;
    }

    @Override
    public Boolean cardsByTypeValidator(Set<Card> cards, CreateCardDto createCardDto) {
        return cards.size() == 3 && Objects.equals(createCardDto.cardType(), CardType.valueOf(createCardDto.cardType().toUpperCase()).toString());
    }

    @Override
    public Boolean cardsByColorValidator(Card card, CreateCardDto createCardDto) {
        return Objects.equals(card.getCardColor().toString(), createCardDto.cardMembership()) && Objects.equals(createCardDto.cardType(), card.getCardType().toString());
    }

    @Override
    public Card makeNewCard(Client client, CreateCardDto createCardDto) {
        return new Card(CardType.valueOf(createCardDto.cardType().toUpperCase()), CardColor.valueOf(createCardDto.cardMembership().toUpperCase()), CardNumberGenerator.makeCardNumber(), CvvGenerator.cvvNumber(), LocalDate.now(), LocalDate.now().plusYears(5), client);
    }

    @Override
    public void saveCardRepository(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Boolean isCardTypeEmpty(CreateCardDto createCardDto) {
        return createCardDto.cardType().isBlank();
    }

    @Override
    public Boolean isCardMembership(CreateCardDto createCardDto) {
        return createCardDto.cardMembership().isBlank();
    }

    @Override
    public ResponseEntity<?> cardRequestValidator(Client client, CreateCardDto createCardDto) {

        if(isCardTypeEmpty(createCardDto)){
            return new ResponseEntity<>("The card type field must not be empty", HttpStatus.BAD_REQUEST);
        }

        if(isCardMembership(createCardDto)){
            return new ResponseEntity<>("The card membership field must not be empty", HttpStatus.BAD_REQUEST);
        }

        if (allCardsNumberValidator(client)) {
            return new ResponseEntity<>("You have reached the maximum number of allowed Debit and Credit cards", HttpStatus.FORBIDDEN);
        }

        if(cardsByTypeValidator(getCardByType(client, CardType.valueOf(createCardDto.cardType().toUpperCase())), createCardDto)){
            return new ResponseEntity<>("You have reached the maximum number of allowed " + createCardDto.cardType() + " cards (3)", HttpStatus.FORBIDDEN);
        }

        for(Card card : getCardByType(client, CardType.valueOf(createCardDto.cardType().toUpperCase()))){
            if (cardsByColorValidator(card, createCardDto)) {
                return new ResponseEntity<>("You already have a " + createCardDto.cardMembership() + " " + createCardDto.cardType() + " card.", HttpStatus.FORBIDDEN);
            }
        }
        return null;
    }

    @Override
    public ResponseEntity<?> newCardForClient(Authentication authentication, CreateCardDto createCardDto) {

        Client client = clientService.getClientByEmail(authentication);

        ResponseEntity<?> validatorResponse = cardRequestValidator(client, createCardDto);
        if(validatorResponse != null){
            return validatorResponse;
        }

        Card card = makeNewCard(client, createCardDto);
        clientService.addCardToClient(client, card);
        saveCardRepository(card);
        clientService.saveClientRepository(authentication);

        return new ResponseEntity<>("Card created", HttpStatus.CREATED);
    }

    @Override
    public Set<Card> getAllCards(Client client) {
        return client.getCards();
    }

    @Override
    public Set<CardDto> getCardsDto(Set<Card> card) {
        return card.stream().map(CardDto::new).collect(Collectors.toSet());
    }
}
