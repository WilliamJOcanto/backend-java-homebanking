package com.Mindhubcohort55.Homebanking.repositories;

import com.Mindhubcohort55.Homebanking.models.ClientLoan;
import com.Mindhubcohort55.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    ClientLoan findByLoan(Loan loan);
}
