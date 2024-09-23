package com.Mindhubcohort55.Homebanking.services;

import com.Mindhubcohort55.Homebanking.dtos.LoginDto;
import com.Mindhubcohort55.Homebanking.dtos.RegisterDto;
import com.Mindhubcohort55.Homebanking.models.Account;
import com.Mindhubcohort55.Homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthorizationService {
    Boolean textFieldValidator(String textField);
    Authentication authenticateUser(String userName, String password);
    UserDetails getUserDetails(String username);
    String generateNewJwt(UserDetails userDetails);
    ResponseEntity<?> loginFieldsValidator(String email, String password);
    ResponseEntity<?> authenticateAndMakeToken(LoginDto loginDto);
    Boolean isClientExists(String userName);
    Client createClient(String firstName, String lastName, String email, String password);
    void saveNewClient(Client client);
    void addNewAccountToNewClient(Client client, Account account);
    ResponseEntity<?> registerFieldsValidator(String firstName, String lastName, String email, String password);
    ResponseEntity<?> registerClient(RegisterDto registerDto);
}
