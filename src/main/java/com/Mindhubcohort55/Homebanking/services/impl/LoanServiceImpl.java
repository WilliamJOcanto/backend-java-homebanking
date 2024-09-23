package com.Mindhubcohort55.Homebanking.services.impl;

import com.Mindhubcohort55.Homebanking.dtos.ApplyLoanDto;
import com.Mindhubcohort55.Homebanking.dtos.LoanDto;
import com.Mindhubcohort55.Homebanking.models.*;
import com.Mindhubcohort55.Homebanking.repositories.*;
import com.Mindhubcohort55.Homebanking.services.AccountService;
import com.Mindhubcohort55.Homebanking.services.ClientService;
import com.Mindhubcohort55.Homebanking.services.LoanService;
import com.Mindhubcohort55.Homebanking.services.TransactionService;
import com.Mindhubcohort55.Homebanking.utils.PaymentRateCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientLoanRepository clientLoanRepository;


    @Override
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public List<Loan> getAllAvailableLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Set<ClientLoan> getAllClientLoans(Client client) {
        return client.getClientLoans();
    }

    @Override
    public Boolean allClientLoansValidator(List<Loan> loansAvailable, Set<ClientLoan> clientLoansExisting) {
        return loansAvailable.size() == clientLoansExisting.size();
    }

    @Override
    public Boolean loanByTypeValidator(Loan loan) {
        return loan == null;
    }

    @Override
    public Boolean clientExistingLoan(ClientLoan clientLoan, Loan loan) {
        return clientLoan.getLoan() == loan;
    }

    @Override
    public Boolean loanAccountFieldValidator(ApplyLoanDto applyLoanDto) {
        return applyLoanDto.sourceAccount() == null || applyLoanDto.sourceAccount().isBlank();
    }

    @Override
    public Boolean amountFieldValidator(ApplyLoanDto applyLoanDto) {
        return applyLoanDto.amount() == null || applyLoanDto.amount() <= 0;
    }

    @Override
    public Boolean paymentFieldValidator(ApplyLoanDto applyLoanDto) {
        return applyLoanDto.payment() == null || applyLoanDto.payment() <= 0;
    }

    @Override
    public Boolean maxAmountValidator(ApplyLoanDto applyLoanDto, Loan loan) {
        return applyLoanDto.amount() > loan.getMaxAmount();
    }

    @Override
    public Boolean existingPaymentValidator(ApplyLoanDto applyLoanDto, Loan loan) {
        return loan.getPayments().contains(applyLoanDto.payment());
    }

    @Override
    public ClientLoan makeNewClientLoan(ApplyLoanDto applyLoanDto) {
        return new ClientLoan(applyLoanDto.amount() + (applyLoanDto.amount()* PaymentRateCalculator.createPaymentRate(applyLoanDto.payment())), applyLoanDto.payment());
    }

    @Override
    public void addClientLoanToLoan(Loan loan, ClientLoan clientLoan) {
        loan.addClientLoan(clientLoan);
    }

    @Override
    public void saveClientLoanRepository(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

    @Override
    public void saveLoanRepository(Loan loan) {
        loanRepository.save(loan);
    }

    @Override
    public ResponseEntity<?> clientLoanRequestValidator(Client client, Loan loan, ApplyLoanDto applyLoanDto, Account account) {

        if(allClientLoansValidator(getAllAvailableLoans(),getAllClientLoans(client))){
            return new ResponseEntity<>("You have already applied to all the loans available on the platform", HttpStatus.FORBIDDEN);
        }
        if(loanByTypeValidator(loan)){
            return new ResponseEntity<>("The type of loan requested does not exist", HttpStatus.FORBIDDEN);
        }
        for(ClientLoan clientLoan : getAllClientLoans(client)){
            if(clientExistingLoan(clientLoan, loan)){
                return new ResponseEntity<>("Sorry, you cannot apply for this loan because you already have a " + loan.getName() + " loan assigned to you.", HttpStatus.FORBIDDEN);
            }
        }
        if(loanAccountFieldValidator(applyLoanDto)){
            return new ResponseEntity<>("The source account field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(amountFieldValidator(applyLoanDto)){
            return new ResponseEntity<>("Enter a valid amount greater than zero", HttpStatus.FORBIDDEN);
        }
        if(paymentFieldValidator(applyLoanDto)){
            return new ResponseEntity<>("Enter a valid payment available for your loan type", HttpStatus.FORBIDDEN);
        }
        if(maxAmountValidator(applyLoanDto, loan)){
            return new ResponseEntity<>("The requested amount exceeds the maximum allowed for the " + loan.getName() + " loan", HttpStatus.FORBIDDEN);
        }
        if(!existingPaymentValidator(applyLoanDto, loan)){
            return new ResponseEntity<>("The chosen payment is not available for the " + loan.getName() + " loan", HttpStatus.FORBIDDEN);
        }
        if(!transactionService.isAccountExist(applyLoanDto.sourceAccount())){
            return new ResponseEntity<>("The source account entered does not exist", HttpStatus.FORBIDDEN);
        }
        if(!transactionService.isAccountBelongsToClient(account, client)){
            return new ResponseEntity<>("The source account entered does not belong to the client", HttpStatus.FORBIDDEN);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> newLoanForClient(Authentication authentication, ApplyLoanDto applyLoanDto) {

        Client client = clientService.getClientByEmail(authentication);
        Account account = accountService.getAccountByNumber(applyLoanDto.sourceAccount());
        Loan loan = getLoanById(applyLoanDto.id());

        ResponseEntity<?> validatorResponse = clientLoanRequestValidator(client, loan, applyLoanDto, account);
        if(validatorResponse != null){
            return validatorResponse;
        }

        ClientLoan clientLoan = makeNewClientLoan(applyLoanDto);

        clientService.addClientLoanToClient(client, clientLoan);
        addClientLoanToLoan(loan, clientLoan);
        saveClientLoanRepository(clientLoan);
        clientService.saveClientRepository(authentication);
        saveLoanRepository(loan);

        Transaction loanTransaction = transactionService.createTransaction(TransactionType.CREDIT, applyLoanDto.amount(), loan.getName(), " loan approved");
        accountService.addTransactionToAccount(account, loanTransaction);
        transactionService.saveTransaction(loanTransaction);

        transactionService.setAccountBalance(account, applyLoanDto.amount());
        accountService.saveAccountRepository(account);

        return new ResponseEntity<>("Loan application successfully approved", HttpStatus.CREATED);
    }

    @Override
    public List<LoanDto> getLoanDtos(List<Loan> loans) {
        return loans.stream().map(LoanDto :: new).collect(Collectors.toList());
    }

    @Override
    public Boolean isLoansAvailableEmpty(List<Loan> loans) {
        return loans.isEmpty();
    }

    @Override
    public Boolean isClientLoanEmpty(Set<ClientLoan> clientLoans) {
        return clientLoans.isEmpty();
    }

    @Override
    public ResponseEntity<?> getAllAvailableLoanDto(Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication);
        List<Loan> allLoansAvailable = getAllAvailableLoans();
        Set<ClientLoan> clientExistingLoans = client.getClientLoans();

        if(isClientLoanEmpty(clientExistingLoans)){
            return new ResponseEntity<>(getLoanDtos(allLoansAvailable), HttpStatus.OK);
        }
        for(ClientLoan clientLoan : clientExistingLoans){
            allLoansAvailable.remove(clientLoan.getLoan());
        }
        if(isLoansAvailableEmpty(allLoansAvailable)){
            return new ResponseEntity<>("You have already applied for all the loans available on the platform! Currently, there are no other options for you at this time.", HttpStatus.OK);
        }
        return new ResponseEntity<>(getLoanDtos(allLoansAvailable), HttpStatus.OK);
    }
}
