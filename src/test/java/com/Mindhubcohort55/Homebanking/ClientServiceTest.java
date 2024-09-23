//package com.Mindhubcohort55.Homebanking;
//
//import com.Mindhubcohort55.Homebanking.dtos.ClientDto;
//import com.Mindhubcohort55.Homebanking.models.Account;
//import com.Mindhubcohort55.Homebanking.models.Card;
//import com.Mindhubcohort55.Homebanking.models.Client;
//import com.Mindhubcohort55.Homebanking.models.ClientLoan;
//import com.Mindhubcohort55.Homebanking.repositories.ClientRepository;
//import com.Mindhubcohort55.Homebanking.services.ClientService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class ClientServiceTest {
//
// @Autowired
// private ClientService clientService;
//
// @MockBean
// private ClientRepository clientRepository;
//
// @Test
// void testGetAllClients() {
//  Client client1 = new Client();
//  Client client2 = new Client();
//
//  when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));
//
//  List<Client> allClients = clientService.getAllClient();
//
//  assertThat(allClients, hasSize(2));
// }
//
// @Test
// void testGetAllClientDto(){
//  Client client1 = new Client();
//  Client client2 = new Client();
//  when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));
//
//  List<ClientDto> allClientDto = clientService.getAllClientDto();
//  assertThat(allClientDto, hasSize(2));
// }
//
// @Test
// void testGetClientById(){
//  Long id = 1L;
//  Client client = new Client();
//  when(clientRepository.findById(id)).thenReturn(Optional.of(client));
//
//  Client clientById = clientService.getClientById(id);
//  assertThat(clientById, is(notNullValue()));
//  assertThat(clientById, is(equalTo(client)));
// }
//
// @Test
// void testGetClientDto(){
//  Long id = 1L;
//  Client client = new Client();
//  ClientDto clientDto = new ClientDto(client);
//  when(clientRepository.findById(id)).thenReturn(Optional.of(client));
//
//  ResponseEntity<?> getClientDto = clientService.getClientDto(id);
//  assertThat(getClientDto.getStatusCode(), is(equalTo(HttpStatus.OK)));
// }
//
// @Test
// void testGetClientDto_NotFound(){
//  Long id = 1L;
//  Client client = new Client();
//  when(clientRepository.findById(id)).thenReturn(Optional.empty());
//
//  ResponseEntity<?> clientNotFound = clientService.getClientDto(id);
//  assertThat(clientNotFound.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));
// }
//
// @Test
// void testGetClientByEmail(){
//  Authentication authentication = mock(Authentication.class);
//  when(authentication.getName()).thenReturn("will@email.com");
//
//  Client client = new Client();
//  when(clientRepository.findByEmail("will@email.com")).thenReturn(client);
//
//  Client clientByEmail = clientService.getClientByEmail(authentication);
//  assertThat(clientByEmail, is(equalTo(client)));
// }
//
// @Test
// void testSaveClientRepository(){
//  Client client = new Client();
//  client.setEmail("will@email.com");
//  when(clientRepository.findByEmail("will@email.com")).thenReturn(client);
//
//  Authentication authentication = mock(Authentication.class);
//  when(authentication.getName()).thenReturn("will@email.com");
//
//  clientService.saveClientRepository(authentication);
//  verify(clientRepository).save(client);
// }
//
// @Test
// void testAddAccountToClient(){
//  Client client = new Client();
//  client.setEmail("will@email.com");
//  Account account = new Account();
//
//  when(clientRepository.findByEmail("will@email.com")).thenReturn(client);
//
//  Authentication authentication = mock(Authentication.class);
//  when(authentication.getName()).thenReturn("will@email.com");
//
//  clientService.addAccountToClient(account, authentication);
//  verify(clientRepository).findByEmail("will@email.com");
//  assertThat(client.getAccounts(), is(contains(account)));
// }
//
// @Test
// void testAddCardToClient(){
//  Client client = new Client();
//  Card card = new Card();
//
//  clientService.addCardToClient(client, card);
//  assertThat(client.getCards(), is(contains(card)));
// }
//
// @Test
// void testAddClientLoanToClient(){
//  Client client = new Client();
//  ClientLoan clientLoan = new ClientLoan();
//
//  clientService.addClientLoanToClient(client, clientLoan);
//  assertThat(client.getClientLoans(), is(contains(clientLoan)));
// }
//}
