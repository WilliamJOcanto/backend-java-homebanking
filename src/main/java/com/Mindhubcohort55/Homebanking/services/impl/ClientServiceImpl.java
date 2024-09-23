package com.Mindhubcohort55.Homebanking.services.impl;

import com.Mindhubcohort55.Homebanking.dtos.ClientDto;
import com.Mindhubcohort55.Homebanking.models.Account;
import com.Mindhubcohort55.Homebanking.models.Card;
import com.Mindhubcohort55.Homebanking.models.Client;
import com.Mindhubcohort55.Homebanking.models.ClientLoan;
import com.Mindhubcohort55.Homebanking.repositories.ClientRepository;
import com.Mindhubcohort55.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    public ClientRepository clientRepository;

    @Override
    public List<Client> getAllClient() {
        return clientRepository.findAll();
    }

    @Override
    public List<ClientDto> getAllClientDto() {
        return getAllClient().stream().map(ClientDto::new).collect(Collectors.toList());
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> getClientDto(Long id) {

        Client client = getClientById(id);

        if(client == null){return new ResponseEntity<>("Client whit the id: " + id + ", not found", HttpStatus.NOT_FOUND);}

        return new ResponseEntity<>(new ClientDto(client), HttpStatus.OK);
    }

    @Override
    public Client getClientByEmail(Authentication authentication) {
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public void saveClientRepository(Authentication authentication) {
        clientRepository.save(getClientByEmail(authentication));
    }

    @Override
    public void addAccountToClient(Account account, Authentication authentication) {
        getClientByEmail(authentication).addAccounts(account);
    }

    @Override
    public void addCardToClient(Client client, Card card) {
        client.addCard(card);
    }

    @Override
    public void addClientLoanToClient(Client client, ClientLoan clientLoan) {
        client.addClientLoan(clientLoan);
    }
}
