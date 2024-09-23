package com.Mindhubcohort55.Homebanking.repositories;

import com.Mindhubcohort55.Homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Boolean existsByName(String name);
    Loan findByName(String name);
}
