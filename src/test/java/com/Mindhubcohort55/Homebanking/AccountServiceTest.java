//package com.Mindhubcohort55.Homebanking;
//
//import com.Mindhubcohort55.Homebanking.dtos.AccountDto;
//import com.Mindhubcohort55.Homebanking.models.Account;
//import com.Mindhubcohort55.Homebanking.models.Client;
//import com.Mindhubcohort55.Homebanking.models.Transaction;
//import com.Mindhubcohort55.Homebanking.repositories.AccountRepository;
//import com.Mindhubcohort55.Homebanking.services.AccountService;
//import com.Mindhubcohort55.Homebanking.services.ClientService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class AccountServiceTest {
//
//    @MockBean
//    private ClientService clientService;
//
//    @MockBean
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private AccountService accountService;
//
//    @Test
//    public void testGetAllAccounts() {
//        Account account1 = new Account();
//        Account account2 = new Account();
//        when(accountRepository.findAll()).thenReturn(Arrays.asList(account1, account2));
//
//        List<Account> accounts = accountService.getAllAccounts();
//
//        assertThat(accounts, hasSize(2));
//    }
//
//    @Test
//    public void testGetAllAccountsDto() {
//        Account account1 = new Account();
//        Account account2 = new Account();
//
//        List<Account> accounts = new ArrayList<>();
//        accounts.add(account1);
//        accounts.add(account2);
//
//        List<AccountDto> dtos = accountService.getAllAccountsDto(accounts);
//
//        assertThat(dtos, hasSize(2));
//    }
//
//    @Test
//    public void testGetAccountById() {
//        Long accountId = 1L;
//        Account account = new Account();
//        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
//
//        Account accountById = accountService.getAccountById(accountId);
//        assertThat(accountById, is(notNullValue()));
//        assertThat(accountById, is(equalTo(account)));
//    }
//
//    @Test
//    public void testGetAccountDto() {
//        Long accountId = 1L;
//        Account account = new Account();
//        AccountDto accountDto = new AccountDto(account);
//        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
//
//        ResponseEntity<?> accountDtoById = accountService.getAccountDto(accountId);
//        assertThat(accountDtoById.getStatusCode(), is(equalTo(HttpStatus.OK)));
//    }
//
//    @Test
//    public void testAccountNumberValidator(){
//        Client client = new Client();
//        client.setEmail("will@email.com");
//        for(int i = 0; i<3; i++){
//            client.addAccounts(new Account());
//        }
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//
//        Boolean setAccountSizeValidator = accountService.accountNumberValidator(authentication);
//        assertThat(setAccountSizeValidator, is(equalTo(true)));
//    }
//
//    @Test
//    public void testMakeNewAccount() {
//        Account newAccount = accountService.makeNewAccount();
//        assertThat(newAccount, is(notNullValue()));
//        assertThat(newAccount.getNumber(), is(notNullValue()));
//        assertThat(newAccount.getBalance(), is(notNullValue()));
//    }
//
//    @Test
//    public void testSaveAccountRepository() {
//        Account account = new Account();
//        accountService.saveAccountRepository(account);
//        verify(accountRepository).save(account);
//    }
//
//    @Test
//    public void testAccountRequestValidator() {
//        Client client = new Client();
//        client.setEmail("will@email.com");
//        for(int i = 0; i<2; i++){
//            client.addAccounts(new Account());
//        }
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//
//        Boolean setAccountSizeValidator = accountService.accountNumberValidator(authentication);
//
//        ResponseEntity<?> accountRequestValidator = accountService.accountRequestValidator(authentication);
//        assertThat(accountRequestValidator, is(equalTo(null)));
//    }
//
//    @Test
//    public void testNewAccountForClient() {
//        Client client = new Client();
//        client.setEmail("will@email.com");
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//
//
//
//        ResponseEntity<?> response = accountService.newAccountForClient(authentication);
//        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
//    }
//
//    @Test
//    public void testGetAccountsByOwner() {
//        Client client = new Client();
//        client.setEmail("will@email.com");
//        Account account1 = new Account();
//        Account account2 = new Account();
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//        when(accountRepository.findByOwner(client)).thenReturn(Arrays.asList(account1, account2));
//
//        List<Account> accountsByOwner = accountService.getAccountsByOwner(authentication);
//        assertThat(accountsByOwner, is(notNullValue()));
//        assertThat(accountsByOwner, hasSize(2));
//    }
//    @Test
//    public void testGetAccountByNumber() {
//        String accountNumber = "123456";
//        Account account = new Account();
//        when(accountRepository.findByNumber(accountNumber)).thenReturn(account);
//
//        Account accountsByNumber = accountService.getAccountByNumber(accountNumber);
//        assertThat(accountsByNumber, is(notNullValue()));
//        assertThat(accountsByNumber, is(equalTo(account)));
//    }
//
//    @Test
//    public void testAddTransactionToAccount() {
//        Account account = new Account();
//        Transaction transaction = new Transaction();
//        accountService.addTransactionToAccount(account, transaction);
//        assertThat(account.getTransactions(), is(contains(transaction)));
//    }
//
//}
