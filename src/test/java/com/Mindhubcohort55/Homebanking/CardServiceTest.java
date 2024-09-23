//package com.Mindhubcohort55.Homebanking;
//
//import com.Mindhubcohort55.Homebanking.dtos.CardDto;
//import com.Mindhubcohort55.Homebanking.dtos.CreateCardDto;
//import com.Mindhubcohort55.Homebanking.models.Card;
//import com.Mindhubcohort55.Homebanking.models.CardColor;
//import com.Mindhubcohort55.Homebanking.models.CardType;
//import com.Mindhubcohort55.Homebanking.models.Client;
//import com.Mindhubcohort55.Homebanking.repositories.CardRepository;
//import com.Mindhubcohort55.Homebanking.services.CardService;
//import com.Mindhubcohort55.Homebanking.services.ClientService;
//import com.Mindhubcohort55.Homebanking.utils.CardNumberGenerator;
//import com.Mindhubcohort55.Homebanking.utils.CvvGenerator;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//
//import java.time.LocalDate;
//import java.util.Set;
//
//import org.mockito.ArgumentCaptor;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class CardServiceTest {
//
//    @Autowired
//    private CardService cardService;
//
//    @MockBean
//    private CardRepository cardRepository;
//
//    @MockBean
//    private ClientService clientService;
//
//    @Test
//    void testGetCardByType(){
//        Client client = new Client();
//        client.setFirstName("Will");
//        client.setLastName("Ocanto");
//        Card card1 = new Card(CardType.CREDIT, CardColor.SILVER, CardNumberGenerator.makeCardNumber(), CvvGenerator.cvvNumber(), LocalDate.now(), LocalDate.now().plusYears(5), client);
//        Card card2 = new Card(CardType.DEBIT, CardColor.TITANIUM, CardNumberGenerator.makeCardNumber(), CvvGenerator.cvvNumber(), LocalDate.now(), LocalDate.now().plusYears(5), client);
//
//        client.addCard(card1);
//        client.addCard(card2);
//        Set<Card> cardByType = cardService.getCardByType(client, CardType.CREDIT);
//
//        assertThat(cardByType, is(hasSize(1)));
//        assertThat(cardByType.iterator().next().getCardType(), is(equalTo(CardType.CREDIT)));
//    }
//
//    @Test
//    void testAllCardsNumberValidator(){
//        Client client = new Client();
//        for (int i = 0; i < 6; i++) {
//            client.addCard(new Card(CardType.CREDIT, CardColor.GOLD, "1234567890123456", "123", LocalDate.now(), LocalDate.now().plusYears(5), client));
//        }
//
//        Boolean sizeSetCards = cardService.allCardsNumberValidator(client);
//        assertThat(sizeSetCards, is(equalTo(true)));
//    }
//
//    @Test
//    void testCardsByTypeValidator(){
//        Client client = new Client();
//        for (int i = 0; i < 3; i++) {
//            client.addCard(new Card(CardType.DEBIT, CardColor.SILVER, "1234567890123456", "123", LocalDate.now(), LocalDate.now().plusYears(5), client));
//        }
//        CreateCardDto createCardDto = new CreateCardDto("DEBIT", "SILVER");
//
//        Boolean cardByTypeValidator = cardService.cardsByTypeValidator(client.getCards(), createCardDto);
//        assertThat(cardByTypeValidator, is(equalTo(true)));
//    }
//
//    @Test
//    void testCardsByColorValidator(){
//        Client client = new Client();
//        Card card = new Card(CardType.DEBIT, CardColor.SILVER, "1234567890123456", "123", LocalDate.now(), LocalDate.now().plusYears(5), client);
//        CreateCardDto createCardDto = new CreateCardDto("DEBIT", "SILVER");
//
//        Boolean cardByColorValidator = cardService.cardsByColorValidator(card, createCardDto);
//        assertThat(cardByColorValidator, is(equalTo(true)));
//    }
//
//    @Test
//    void testMakeNewCard(){
//        Client client = new Client();
//        CreateCardDto createCardDto = new CreateCardDto("DEBIT", "SILVER");
//
//        Card card = cardService.makeNewCard(client, createCardDto);
//        assertThat(card.getCardType(), is(equalTo(CardType.DEBIT)));
//        assertThat(card.getCardColor(), is(equalTo(CardColor.SILVER)));
//    }
//
//    @Test
//    void testSaveCardRepository(){
//        Client client = new Client();
//        Card card = new Card(CardType.DEBIT, CardColor.SILVER, "1234567890123456", "123", LocalDate.now(), LocalDate.now().plusYears(5), client);
//
//        cardService.saveCardRepository(card);
//        verify(cardRepository).save(card);
//    }
//
//    @Test
//    void testIsCardTypeEmpty(){
//        CreateCardDto createCardDto = new CreateCardDto("", "SILVER");
//        Boolean isCardTypeEmpty = cardService.isCardTypeEmpty(createCardDto);
//        assertThat(isCardTypeEmpty, is(equalTo(true)));
//    }
//
//    @Test
//    void testIsCardMembership(){
//        CreateCardDto createCardDto = new CreateCardDto("DEBIT", "");
//        Boolean isCardTypeEmpty = cardService.isCardMembership(createCardDto);
//        assertThat(isCardTypeEmpty, is(equalTo(true)));
//    }
//
//    @Test
//    void testCardRequestValidator(){
//        Client client = new Client();
//        Card card = new Card(CardType.DEBIT, CardColor.SILVER, "1234567890123456", "123", LocalDate.now(), LocalDate.now().plusYears(5), client);
//        client.addCard(card);
//        CreateCardDto createCardDto = new CreateCardDto("DEBIT", "GOLD");
//
//        ResponseEntity<?> responseValidator = cardService.cardRequestValidator(client, createCardDto);
//        assertThat(responseValidator, is(equalTo(null)));
//    }
//
//    @Test
//    void testNewCardForClient() {
//        Client client = new Client();
//        client.setEmail("will@email.com");
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//        CreateCardDto createCardDto = new CreateCardDto("DEBIT", "SILVER");
//
//        ResponseEntity<?> newCardResponse = cardService.newCardForClient(authentication, createCardDto);
//        assertThat(newCardResponse.getStatusCode(), is(HttpStatus.CREATED));
//
//        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
//        verify(cardRepository).save(cardCaptor.capture());
//
//        Card savedCard = cardCaptor.getValue();
//
//        assertThat(savedCard, is(notNullValue()));
//        assertThat(savedCard.getCardType(), is(CardType.DEBIT));
//        assertThat(savedCard.getCardColor(), is(CardColor.SILVER));
//        assertThat(savedCard.getCardNumber(), not(emptyOrNullString()));
//        assertThat(savedCard.getCvv(), not(emptyOrNullString()));
//
//        verify(clientService).addCardToClient(client, savedCard);
//        verify(clientService).saveClientRepository(eq(authentication));
//    }
//
//    @Test
//    void testGetAllCards() {
//        Client client = new Client();
//        client.setEmail("will@email.com");
//        Card card1 = new Card(CardType.DEBIT, CardColor.SILVER, "1234567890123456", "123", LocalDate.now(), LocalDate.now().plusYears(5), client);
//        Card card2 = new Card(CardType.CREDIT, CardColor.GOLD, "6543210987654321", "321", LocalDate.now(), LocalDate.now().plusYears(5), client);
//        client.addCard(card1);
//        client.addCard(card2);
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//
//        Set<Card> result = cardService.getAllCards(client);
//
//        assertThat(result, hasSize(2));
//    }
//
//    @Test
//    void testGetCardsDto() {
//        Client client = new Client();
//        Card card1 = new Card(CardType.DEBIT, CardColor.SILVER, "1234567890123456", "123", LocalDate.now(), LocalDate.now().plusYears(5), client);
//        Card card2 = new Card(CardType.CREDIT, CardColor.GOLD, "6543210987654321", "321", LocalDate.now(), LocalDate.now().plusYears(5), client);
//        client.addCard(card1);
//        client.addCard(card2);
//
//        Set<Card> cards = client.getCards();
//
//        Set<CardDto> cardDtos = cardService.getCardsDto(cards);
//
//        assertThat(cardDtos, hasSize(2));
//        assertThat(cardDtos.iterator().next().getCardtype(), is(CardType.DEBIT));
//    }
//}

