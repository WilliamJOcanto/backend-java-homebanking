package com.Mindhubcohort55.Homebanking.services;

import com.Mindhubcohort55.Homebanking.dtos.ApplyLoanDto;
import com.Mindhubcohort55.Homebanking.dtos.LoanDto;
import com.Mindhubcohort55.Homebanking.models.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface LoanService {
    Loan getLoanById(Long id);
    List<Loan> getAllAvailableLoans();
    Set<ClientLoan> getAllClientLoans(Client client);
    Boolean allClientLoansValidator(List<Loan> loansAvailable, Set<ClientLoan> clientLoansExisting);
    Boolean loanByTypeValidator(Loan loan);
    Boolean clientExistingLoan(ClientLoan clientLoan, Loan loan);
    Boolean loanAccountFieldValidator(ApplyLoanDto applyLoanDto);
    Boolean amountFieldValidator(ApplyLoanDto applyLoanDto);
    Boolean paymentFieldValidator(ApplyLoanDto applyLoanDto);
    Boolean maxAmountValidator(ApplyLoanDto applyLoanDto, Loan loan);
    Boolean existingPaymentValidator(ApplyLoanDto applyLoanDto, Loan loan);
    ClientLoan makeNewClientLoan(ApplyLoanDto applyLoanDto);
    void addClientLoanToLoan(Loan loan, ClientLoan clientLoan);
    void saveClientLoanRepository(ClientLoan clientLoan);
    void saveLoanRepository(Loan loan);
    ResponseEntity<?> newLoanForClient(Authentication authentication, ApplyLoanDto applyLoanDto);
    List<LoanDto> getLoanDtos(List<Loan> loans);
    Boolean isLoansAvailableEmpty(List<Loan> loans);
    Boolean isClientLoanEmpty(Set<ClientLoan> clientLoans);
    ResponseEntity<?> getAllAvailableLoanDto(Authentication authentication);
    ResponseEntity<?> clientLoanRequestValidator(Client client, Loan loan, ApplyLoanDto applyLoanDto, Account account);

}

