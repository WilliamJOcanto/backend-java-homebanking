package com.Mindhubcohort55.Homebanking.controllers;

import com.Mindhubcohort55.Homebanking.dtos.CreateCardDto;
import com.Mindhubcohort55.Homebanking.services.AccountService;
import com.Mindhubcohort55.Homebanking.services.CardService;
import com.Mindhubcohort55.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/clients")
public class CardController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private CardService cardService;

    @PostMapping("/current/cards")
    public ResponseEntity<?> createCard(@RequestBody CreateCardDto createCardDto, Authentication authentication){
        try {
            return cardService.newCardForClient(authentication, createCardDto);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error creating card: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/current/cards")
    public ResponseEntity<?> getClientCards(Authentication authentication){
        return new ResponseEntity<>(cardService.getCardsDto(cardService.getAllCards(clientService.getClientByEmail(authentication))), HttpStatus.OK);
    }
}
