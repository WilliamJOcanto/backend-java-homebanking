package com.Mindhubcohort55.Homebanking.repositories;

import com.Mindhubcohort55.Homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Boolean existsByCardNumber(String cardNumber);
    Boolean existsByCvv(String cvv);
}
