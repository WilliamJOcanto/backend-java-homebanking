package com.Mindhubcohort55.Homebanking.repositories;

import com.Mindhubcohort55.Homebanking.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
