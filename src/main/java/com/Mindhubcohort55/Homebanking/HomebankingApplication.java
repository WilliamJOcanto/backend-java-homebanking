package com.Mindhubcohort55.Homebanking;

import com.Mindhubcohort55.Homebanking.models.*;
import com.Mindhubcohort55.Homebanking.repositories.*;
import com.Mindhubcohort55.Homebanking.utils.CardNumberGenerator;
import com.Mindhubcohort55.Homebanking.utils.CvvGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {SpringApplication.run(HomebankingApplication.class, args);}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository){

		return args -> {

		Client will = new Client("Will", "Ocanto", "Ocanto23@gmail.com", passwordEncoder.encode("2314"));

		Account accountWill = new Account("VIN003", LocalDateTime.now(), 100000.00);
		Account account2Will = new Account("VIN004", LocalDateTime.now(), 200000.00);

		Transaction internetPayment = new Transaction(TransactionType.DEBIT, -100.00, "internet payment", LocalDateTime.now());
		Transaction servicesPayment = new Transaction(TransactionType.DEBIT, -200.00, "services payment", LocalDateTime.now());
		Transaction rentPayment = new Transaction(TransactionType.DEBIT, -400.00, "rent payment", LocalDateTime.now());

		Transaction chargingClient = new Transaction(TransactionType.CREDIT, 200.00, "charging client", LocalDateTime.now());
		Transaction salaryPayment = new Transaction(TransactionType.CREDIT, 500.00, "salary payment", LocalDateTime.now());
		Transaction debtPayment = new Transaction(TransactionType.CREDIT, 400.00, "debt payment", LocalDateTime.now());

		accountWill.addTransaction(internetPayment);
		accountWill.addTransaction(servicesPayment);
		accountWill.addTransaction(rentPayment);

		account2Will.addTransaction(chargingClient);
		account2Will.addTransaction(salaryPayment);
		account2Will.addTransaction(debtPayment);

		will.addAccounts(accountWill);
		will.addAccounts(account2Will);

		clientRepository.save(will);
		accountRepository.save(accountWill);
		accountRepository.save(account2Will);
		transactionRepository.save(internetPayment);
		transactionRepository.save(servicesPayment);
		transactionRepository.save(rentPayment);
		transactionRepository.save(chargingClient);
		transactionRepository.save(salaryPayment);
		transactionRepository.save(debtPayment);


		Client melba = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("1423"));

		Account accountMelba = new Account("VIN001", LocalDateTime.now(), 5000.00);
		Account account2Melba = new Account("VIN002", LocalDateTime.now(), 7500.00);

		Transaction taxesPayment = new Transaction(TransactionType.DEBIT, -200.00, "taxes payment", LocalDateTime.now());
		Transaction coursePayment = new Transaction(TransactionType.DEBIT, -300.00, "couse payment", LocalDateTime.now());
		Transaction streamingPayment = new Transaction(TransactionType.DEBIT, -100.00, "streaming payment", LocalDateTime.now());

		Transaction bonuses = new Transaction(TransactionType.CREDIT, 500.00, "bonuses", LocalDateTime.now());
		Transaction refund = new Transaction(TransactionType.CREDIT, 234.00, "refund", LocalDateTime.now());
		Transaction settlement = new Transaction(TransactionType.CREDIT, 600.00, "settlement", LocalDateTime.now());

		accountMelba.addTransaction(taxesPayment);
		accountMelba.addTransaction(coursePayment);
		accountMelba.addTransaction(streamingPayment);

		account2Melba.addTransaction(bonuses);
		account2Melba.addTransaction(refund);
		account2Melba.addTransaction(settlement);

		melba.addAccounts(accountMelba);
		melba.addAccounts(account2Melba);

		clientRepository.save(melba);
		accountRepository.save(accountMelba);
		accountRepository.save(account2Melba);
		transactionRepository.save(taxesPayment);
		transactionRepository.save(coursePayment);
		transactionRepository.save(streamingPayment);
		transactionRepository.save(bonuses);
		transactionRepository.save(refund);
		transactionRepository.save(settlement);


		Loan mortgage = new Loan("Mortgage", 500000.00, Arrays.asList(12, 24, 36, 48, 60, 72));
		Loan personal = new Loan("Personal", 100000.00,  Arrays.asList(6,12,24));
		Loan automotive = new Loan("Automotive", 300000.00 , Arrays.asList(6,12,24,36));
		loanRepository.save(mortgage);
		loanRepository.save(personal);
		loanRepository.save(automotive);


		ClientLoan clientLoan1 = new ClientLoan( 400.000, 60);
		melba.addClientLoan(clientLoan1);
		mortgage.addClientLoan(clientLoan1);
		clientLoanRepository.save(clientLoan1);

		ClientLoan clientLoan2 = new ClientLoan( 50.000, 12);
		melba.addClientLoan(clientLoan2);
		personal.addClientLoan(clientLoan2);
		clientLoanRepository.save(clientLoan2);


		ClientLoan clientLoan3 = new ClientLoan(100.000, 24);
		will.addClientLoan(clientLoan3);
		personal.addClientLoan(clientLoan3);
		clientLoanRepository.save(clientLoan3);

		ClientLoan clientLoan4 = new ClientLoan(200.000, 36);
		will.addClientLoan(clientLoan4);
		automotive.addClientLoan(clientLoan4);
		clientLoanRepository.save(clientLoan4);


		Card cardGold = new Card(CardType.DEBIT, CardColor.GOLD, CardNumberGenerator.makeCardNumber(), CvvGenerator.cvvNumber(), LocalDate.now(), LocalDate.now().plusYears(5), melba);
		Card cardTitanium = new Card(CardType.CREDIT, CardColor.TITANIUM, CardNumberGenerator.makeCardNumber(), CvvGenerator.cvvNumber(), LocalDate.now(), LocalDate.now().plusYears(5), melba);

		melba.addCard(cardGold);
		melba.addCard(cardTitanium);

		cardRepository.save(cardGold);
		cardRepository.save(cardTitanium);


		Card cardSilver = new Card(CardType.CREDIT, CardColor.SILVER, CardNumberGenerator.makeCardNumber(), CvvGenerator.cvvNumber(), LocalDate.now(), LocalDate.now().plusYears(5), will);
		will.addCard(cardSilver);
		cardRepository.save(cardSilver);

		System.out.println(will);
		};
	}
}
