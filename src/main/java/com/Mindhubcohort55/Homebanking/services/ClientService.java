package com.Mindhubcohort55.Homebanking.services;


import com.Mindhubcohort55.Homebanking.dtos.ClientDto;
import com.Mindhubcohort55.Homebanking.models.Account;
import com.Mindhubcohort55.Homebanking.models.Card;
import com.Mindhubcohort55.Homebanking.models.Client;
import com.Mindhubcohort55.Homebanking.models.ClientLoan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
   List<Client> getAllClient();
   List<ClientDto> getAllClientDto();
   Client getClientById(Long id);
   ResponseEntity<?> getClientDto(Long id);
   Client getClientByEmail(Authentication authentication);
   void saveClientRepository(Authentication authentication);
   void addAccountToClient(Account account, Authentication authentication);
   void addCardToClient(Client client, Card card);
   void addClientLoanToClient(Client client, ClientLoan clientLoan);
}
