//package com.Mindhubcohort55.Homebanking;
//
//import com.Mindhubcohort55.Homebanking.dtos.MakeTransactionDto;
//import com.Mindhubcohort55.Homebanking.models.Account;
//import com.Mindhubcohort55.Homebanking.models.Client;
//import com.Mindhubcohort55.Homebanking.models.Transaction;
//import com.Mindhubcohort55.Homebanking.models.TransactionType;
//import com.Mindhubcohort55.Homebanking.repositories.AccountRepository;
//import com.Mindhubcohort55.Homebanking.repositories.TransactionRepository;
//import com.Mindhubcohort55.Homebanking.services.AccountService;
//import com.Mindhubcohort55.Homebanking.services.ClientService;
//import com.Mindhubcohort55.Homebanking.services.TransactionService;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//
//import java.time.LocalDateTime;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class TransactionServiceTest {
//
//    @Autowired
//    private TransactionService transactionService;
//
//    @MockBean
//    private ClientService clientService;
//
//    @MockBean
//    private AccountService accountService;
//
//    @MockBean
//    private AccountRepository accountRepository;
//
//    @MockBean
//    private TransactionRepository transactionRepository;
//
//    @Test
//    public void testSaveTransaction() {
//        Transaction transaction = new Transaction();
//        transactionService.saveTransaction(transaction);
//        verify(transactionRepository).save(transaction);
//    }
//
//    @Test
//    public void testAccountFieldValidator() {
//        String accountField = "";
//        Boolean fieldValidator =  transactionService.accountFieldValidator(accountField);
//        assertThat(fieldValidator, is(true));
//    }
//    @Test
//    public void testAmountTransactionFieldValidator() {
//        MakeTransactionDto dto = new MakeTransactionDto("VIN001", "VIN002", null, "text");
//        Boolean amountValidator = transactionService.amountTransactionFieldValidator(dto);
//        assertThat(amountValidator, is(true));
//    }
//
//    @Test
//    public void testDescriptionFieldValidator() {
//        MakeTransactionDto dto = new MakeTransactionDto("VIN001", "VIN002", 1000.00, "");
//        Boolean descriptionFieldValidator = transactionService.descriptionFieldValidator(dto);
//        assertThat(descriptionFieldValidator, is(true));
//    }
//
//    @Test
//    public void testIsSourceAndDestinationEquals() {
//        MakeTransactionDto dto = new MakeTransactionDto("VIN001", "VIN001", 1000.00, "text");
//        Boolean accountsEqualsValidator = transactionService.isSourceAndDestinationEquals(dto);
//        assertThat(accountsEqualsValidator, is(true));
//    }
//
//    @Test
//    public void testIsAccountExist() {
//        String accountNumber = "VIN001";
//        when(accountRepository.existsByNumber(accountNumber)).thenReturn(true);
//        Boolean isAccountExists = transactionService.isAccountExist(accountNumber);
//        assertThat(isAccountExists, is(true));
//    }
//
//    @Test
//    public void testIsAccountBelongsToClient() {
//        Account account = new Account();
//        Client client = new Client();
//        when(accountRepository.existsByIdAndOwner(account.getId(), client)).thenReturn(true);
//
//        Boolean belongs = transactionService.isAccountBelongsToClient(account, client);
//        assertThat(belongs, is(true));
//
//        when(accountRepository.existsByIdAndOwner(account.getId(), client)).thenReturn(false);
//        belongs = transactionService.isAccountBelongsToClient(account, client);
//        assertThat(belongs, is(false));
//    }
//
//    @Test
//    public void testEnoughBalanceValidator() {
//        Account account = new Account();
//        account.setBalance(100.0);
//        MakeTransactionDto dto = new MakeTransactionDto("VIN001", "VIN002", 200.00, "text");
//
//        Boolean isEnough = transactionService.enoughBalanceValidator(account, dto);
//        assertThat(isEnough, is(true));
//
//        dto = new MakeTransactionDto("VIN001", "VIN002", 50.00, "text");
//        isEnough = transactionService.enoughBalanceValidator(account, dto);
//        assertThat(isEnough, is(false));
//    }
//
//    @Test
//    public void testCreateTransaction() {
//        Transaction transaction = transactionService.createTransaction(TransactionType.DEBIT, 100.0, "description", " complement");
//
//        assertThat(TransactionType.DEBIT, is(equalTo(transaction.getTransactionType())));
//        assertThat(100.0, is(equalTo(transaction.getAmount())));
//        assertThat("description complement", is(equalTo(transaction.getDescription())));
//    }
//
//    @Test
//    public void testSetAccountBalance() {
//        Account account = new Account();
//        account.setBalance(100.0);
//        transactionService.setAccountBalance(account, 50.0);
//
//        assertThat(150.0, is(equalTo(account.getBalance())));
//    }
//
//    @Test
//    public void testTransactionRequestValidator() {
//        MakeTransactionDto dto = new MakeTransactionDto("VIN001", "VIN002", 100.00, "text");
//        Client client = new Client();
//        Account sourceAccount = new Account("VIN001", LocalDateTime.now(), 400.00);
//
//        when(accountRepository.existsByNumber("VIN001")).thenReturn(true);
//        when(accountRepository.existsByNumber("VIN002")).thenReturn(true);
//        when(accountRepository.existsByIdAndOwner(sourceAccount.getId(), client)).thenReturn(true);
//
//        ResponseEntity<?> responseValidator = transactionService.transactionRequestValidator(dto, sourceAccount, client);
//        assertThat(responseValidator, is(equalTo(null)));
//    }
//
//    @Test
//    public void testGenerateTransaction() {
//        MakeTransactionDto dto = new MakeTransactionDto("VIN001", "VIN002", 100.00, "text");
//        Client client = new Client();
//        client.setEmail("will@email.com");
//        Account sourceAccount = new Account("VIN001", LocalDateTime.now(), 400.00);
//        Account destinationAccount = new Account("VIN002", LocalDateTime.now(), 0.00);
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//        when(accountService.getAccountByNumber(dto.sourceAccount())).thenReturn(sourceAccount);
//        when(accountService.getAccountByNumber(dto.destinationAccount())).thenReturn(destinationAccount);
//        when(accountRepository.existsByNumber("VIN001")).thenReturn(true);
//        when(accountRepository.existsByNumber("VIN002")).thenReturn(true);
//        when(accountRepository.existsByIdAndOwner(sourceAccount.getId(), client)).thenReturn(true);
//        when(transactionRepository.save(ArgumentMatchers.any(Transaction.class))).thenReturn(new Transaction());
//
//        ResponseEntity<?> response = transactionService.generateTransaction(authentication, dto);
//        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
//
//        verify(transactionRepository, times(2)).save(ArgumentMatchers.any(Transaction.class));
//        verify(accountService, times(2)).saveAccountRepository(ArgumentMatchers.any(Account.class));
//    }
//}
