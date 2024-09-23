package com.Mindhubcohort55.Homebanking.repositories;

import com.Mindhubcohort55.Homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);
    Boolean existsByEmail(String email);
}
