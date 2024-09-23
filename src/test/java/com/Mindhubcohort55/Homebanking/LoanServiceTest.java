//package com.Mindhubcohort55.Homebanking;
//
//import com.Mindhubcohort55.Homebanking.dtos.ApplyLoanDto;
//import com.Mindhubcohort55.Homebanking.dtos.LoanDto;
//import com.Mindhubcohort55.Homebanking.models.*;
//import com.Mindhubcohort55.Homebanking.repositories.ClientLoanRepository;
//import com.Mindhubcohort55.Homebanking.repositories.LoanRepository;
//import com.Mindhubcohort55.Homebanking.services.AccountService;
//import com.Mindhubcohort55.Homebanking.services.ClientService;
//import com.Mindhubcohort55.Homebanking.services.LoanService;
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
//import java.util.*;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//public class LoanServiceTest {
//
//    @Autowired
//    private LoanService loanService;
//
//    @MockBean
//    private ClientService clientService;
//
//    @MockBean
//    private AccountService accountService;
//
//    @MockBean
//    private TransactionService transactionService;
//
//    @MockBean
//    private LoanRepository loanRepository;
//
//    @MockBean
//    private ClientLoanRepository clientLoanRepository;
//
//    @Test
//    public void testGetLoanById() {
//        Long id = 1L;
//        Loan loan = new Loan();
//        when(loanRepository.findById(id)).thenReturn(Optional.of(loan));
//
//        Loan loanById = loanService.getLoanById(id);
//        assertThat(loanById, is(notNullValue()));
//        assertThat(loanById, is(equalTo(loan)));
//    }
//
//    @Test
//    public void testGetAllAvailableLoans() {
//        List<Loan> loans = Arrays.asList(new Loan(), new Loan());
//        when(loanRepository.findAll()).thenReturn(loans);
//
//        List<Loan> allLoans = loanService.getAllAvailableLoans();
//        assertThat(allLoans, hasSize(2));
//    }
//
//    @Test
//    public void testGetAllClientLoans(){
//        Client client = new Client();
//        ClientLoan clientLoan = new ClientLoan();
//        client.addClientLoan(clientLoan);
//
//        Set<ClientLoan> allClientLoans = loanService.getAllClientLoans(client);
//        assertThat(allClientLoans, hasSize(1));
//    }
//
//    @Test
//    public void testAllClientLoansValidator() {
//        List<Loan> loansAvailable = Arrays.asList(new Loan(), new Loan());
//        Set<ClientLoan> clientLoansExisting = new HashSet<>(Arrays.asList(new ClientLoan(), new ClientLoan()));
//
//        Boolean allClientLoansResult = loanService.allClientLoansValidator(loansAvailable, clientLoansExisting);
//        assertThat(allClientLoansResult, is(true));
//    }
//
//    @Test
//    public void testLoanByTypeValidator() {
//        Loan loan = null;
//        Boolean result = loanService.loanByTypeValidator(loan);
//        assertThat(result, is(true));
//
//        loan = new Loan();
//        result = loanService.loanByTypeValidator(loan);
//        assertThat(result, is(false));
//    }
//
//    @Test
//    public void testClientExistingLoan() {
//        Loan loan = new Loan();
//        ClientLoan clientLoan = new ClientLoan();
//        clientLoan.setLoan(loan);
//
//        Boolean result = loanService.clientExistingLoan(clientLoan, loan);
//        assertThat(result, is(true));
//    }
//
//    @Test
//    public void testLoanAccountFieldValidator() {
//        ApplyLoanDto dto = new ApplyLoanDto(1L, "VIN001", 100.00, 12);
//        Boolean fieldValidator = loanService.loanAccountFieldValidator(dto);
//
//        assertThat(fieldValidator, is(false));
//
//        dto = new ApplyLoanDto(1L, "", 100.00, 12);
//        fieldValidator = loanService.loanAccountFieldValidator(dto);
//        assertThat(fieldValidator, is(true));
//    }
//
//    @Test
//    public void testAmountFieldValidator() {
//        ApplyLoanDto dto = new ApplyLoanDto(1L, "VIN001", null, 12);
//        Boolean fieldValidator = loanService.amountFieldValidator(dto);
//        assertThat(fieldValidator, is(true));
//
//        dto = new ApplyLoanDto(1L, "VIN001", 100.00, 12);
//        fieldValidator = loanService.amountFieldValidator(dto);
//        assertThat(fieldValidator, is(false));
//    }
//
//    @Test
//    public void testPaymentFieldValidator() {
//        ApplyLoanDto dto = new ApplyLoanDto(1L, "VIN001", 100.00, null);
//        Boolean fieldValidator = loanService.paymentFieldValidator(dto);
//        assertThat(fieldValidator, is(true));
//
//        dto = new ApplyLoanDto(1L, "VIN001", 100.00, 12);
//        fieldValidator = loanService.paymentFieldValidator(dto);
//        assertThat(fieldValidator, is(false));
//    }
//
//    @Test
//    public void testMaxAmountValidator() {
//        Loan loan = new Loan();
//        loan.setMaxAmount(5000.0);
//        ApplyLoanDto dto = new ApplyLoanDto(1L, "VIN001", 6000.00, 12);
//
//        Boolean maxAmountValidator = loanService.maxAmountValidator(dto, loan);
//        assertThat(maxAmountValidator, is(true));
//
//        dto = new ApplyLoanDto(1L, "VIN001", 4000.00, 12);
//        maxAmountValidator = loanService.maxAmountValidator(dto, loan);
//        assertThat(maxAmountValidator, is(false));
//    }
//
//    @Test
//    public void testExistingPaymentValidator() {
//        Loan loan = new Loan();
//        loan.setPayments(new ArrayList<>(Arrays.asList(6, 12, 24)));
//        ApplyLoanDto dto = new ApplyLoanDto(1L, "VIN001", 4000.00, 12);
//
//        Boolean resultExistingPayment = loanService.existingPaymentValidator(dto, loan);
//        assertThat(resultExistingPayment, is(true));
//
//        dto = new ApplyLoanDto(1L, "VIN001", 4000.00, 36);
//        resultExistingPayment  = loanService.existingPaymentValidator(dto, loan);
//        assertThat(resultExistingPayment, is(false));
//    }
//
//    @Test
//    public void testMakeNewClientLoan() {
//        ApplyLoanDto dto = new ApplyLoanDto(1L, "VIN001", 4000.00, 12);
//        ClientLoan clientLoan = loanService.makeNewClientLoan(dto);
//        assertThat(clientLoan, is(notNullValue()));
//    }
//
//    @Test
//    public void testClientLoanRequestValidator() {
//        Client client = new Client();
//        Loan loan = new Loan();
//        loan.setMaxAmount(5000.0);
//        loan.setPayments(new ArrayList<>(Arrays.asList(6, 12, 24)));
//        Account account = new Account();
//        List<Loan> loansAvailable = Arrays.asList(new Loan(), new Loan());
//        ClientLoan clientLoan = new ClientLoan();
//        client.addClientLoan(clientLoan);
//        ApplyLoanDto dto = new ApplyLoanDto(5L, "VIN001", 100.00, 12);
//
//        when(loanService.getAllAvailableLoans()).thenReturn(loansAvailable);
//
//        when(transactionService.isAccountExist(ArgumentMatchers.any(String.class))).thenReturn(true);
//        when(transactionService.isAccountBelongsToClient(ArgumentMatchers.any(Account.class), ArgumentMatchers.any(Client.class))).thenReturn(true);
//
//        ResponseEntity<?> responseValidator = loanService.clientLoanRequestValidator(client, loan, dto, account);
//        assertThat(responseValidator, is(nullValue()));
//    }
//
//    @Test
//    public void testNewLoanForClient() {
//        ApplyLoanDto applyLoanDto = new ApplyLoanDto(1L, "VIN001", 100.00, 12);
//        Client client = new Client();
//        client.setEmail("will@email.com");
//        Account account = new Account();
//        Loan loan = new Loan();
//        loan.setMaxAmount(5000.0);
//        loan.setPayments(new ArrayList<>(Arrays.asList(6, 12, 24)));
//        ClientLoan clientLoan = new ClientLoan();
//        client.addClientLoan(clientLoan);
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//        when(accountService.getAccountByNumber(ArgumentMatchers.any(String.class))).thenReturn(account);
//        when(loanRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(loan));
//        when(transactionService.isAccountExist(ArgumentMatchers.any(String.class))).thenReturn(true);
//        when(transactionService.isAccountBelongsToClient(ArgumentMatchers.any(Account.class), ArgumentMatchers.any(Client.class))).thenReturn(true);
//        when(transactionService.createTransaction(ArgumentMatchers.any(TransactionType.class), ArgumentMatchers.any(Double.class), ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class))).thenReturn(new Transaction());
//
//        ResponseEntity<?> response = loanService.newLoanForClient(authentication, applyLoanDto);
//        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
//    }
//
//    @Test
//    public void testGetLoanDtos() {
//        List<Loan> loans = Arrays.asList(new Loan(), new Loan());
//        List<LoanDto> loanDtos = loanService.getLoanDtos(loans);
//        assertThat(loanDtos, hasSize(2));
//    }
//
//    @Test
//    public void testIsLoansAvailableEmpty() {
//        List<Loan> loans = new ArrayList<>();
//        assertThat(loanService.isLoansAvailableEmpty(loans), is(true));
//
//        loans.add(new Loan());
//        assertThat(loanService.isLoansAvailableEmpty(loans), is(false));
//    }
//
//    @Test
//    public void testIsClientLoanEmpty() {
//        Set<ClientLoan> clientLoans = new HashSet<>();
//        assertThat(loanService.isClientLoanEmpty(clientLoans), is(true));
//
//        clientLoans.add(new ClientLoan());
//        assertThat(loanService.isClientLoanEmpty(clientLoans), is(false));
//    }
//
//    @Test
//    public void testGetAllAvailableLoanDto() {
//        Client client = new Client();
//        client.setEmail("will@email.com");
//        List<Loan> loans = Arrays.asList(new Loan(), new Loan());
//
//        Authentication authentication = mock(Authentication.class);
//        when(authentication.getName()).thenReturn("will@email.com");
//        when(clientService.getClientByEmail(authentication)).thenReturn(client);
//        when(loanRepository.findAll()).thenReturn(loans);
//
//
//        ResponseEntity<?> response = loanService.getAllAvailableLoanDto(authentication);
//        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
//    }
//}
