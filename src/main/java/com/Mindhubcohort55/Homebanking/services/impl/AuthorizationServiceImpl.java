package com.Mindhubcohort55.Homebanking.services.impl;

import com.Mindhubcohort55.Homebanking.dtos.LoginDto;
import com.Mindhubcohort55.Homebanking.dtos.RegisterDto;
import com.Mindhubcohort55.Homebanking.models.Account;
import com.Mindhubcohort55.Homebanking.models.Client;
import com.Mindhubcohort55.Homebanking.repositories.ClientRepository;
import com.Mindhubcohort55.Homebanking.services.AccountService;
import com.Mindhubcohort55.Homebanking.services.AuthorizationService;
import com.Mindhubcohort55.Homebanking.servicesSecurity.JwtUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl  implements AuthorizationService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Override
    public Boolean textFieldValidator(String textField) {
        return textField.isBlank();
    }

    @Override
    public Authentication authenticateUser(String userName, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
    }

    @Override
    public UserDetails getUserDetails(String username) {
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public String generateNewJwt(UserDetails userDetails) {
        return jwtUtilService.generateToken(userDetails);
    }

    @Override
    public ResponseEntity<?> loginFieldsValidator(String email, String password) {

        if(textFieldValidator(email)){
            return new ResponseEntity<>("The Email field must not be empty", HttpStatus.BAD_REQUEST);
        }
        if(textFieldValidator(password)){
            return new ResponseEntity<>("The Password field must not be empty", HttpStatus.BAD_REQUEST);
        }
        if(!email.contains(".com")){
            return new ResponseEntity<>("You must enter a valid email", HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> authenticateAndMakeToken(LoginDto loginDto) {

        ResponseEntity<?> validatorResponse = loginFieldsValidator(loginDto.email(), loginDto.password());
        if(validatorResponse != null){
            return validatorResponse;
        }

        authenticateUser(loginDto.email(), loginDto.password());
        final UserDetails userDetails = getUserDetails(loginDto.email());
        final String jwt = generateNewJwt(userDetails);

        return ResponseEntity.ok(jwt);
    }

    @Override
    public Boolean isClientExists(String userName) {
        return clientRepository.existsByEmail(userName);
    }

    @Override
    public Client createClient(String firstName, String lastName, String email, String password) {
        return new Client(firstName, lastName, email, passwordEncoder.encode(password));
    }

    @Override
    public void saveNewClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public void addNewAccountToNewClient(Client client, Account account) {
        client.addAccounts(account);
    }

    @Override
    public ResponseEntity<?> registerFieldsValidator(String firstName, String lastName, String email, String password) {

        if(textFieldValidator(firstName)){
            return new ResponseEntity<>("The Name field must not be empty", HttpStatus.BAD_REQUEST);
        }
        if(textFieldValidator(lastName)){
            return new ResponseEntity<>("The Last Name field must not be empty", HttpStatus.BAD_REQUEST);
        }
        if(textFieldValidator(email)){
            return new ResponseEntity<>("The Email field must not be empty", HttpStatus.FORBIDDEN);
        }
        if(textFieldValidator(password)){
            return new ResponseEntity<>("The Password field must not be empty", HttpStatus.BAD_REQUEST);
        }
        if(password.length() < 8){
            return new ResponseEntity<>("The password should not be less than 8 characters", HttpStatus.BAD_REQUEST);
        }
        if(!email.contains(".com")){
            return new ResponseEntity<>("You must enter a valid email", HttpStatus.BAD_REQUEST);
        }
        if(isClientExists(email)){
            return new ResponseEntity<>("The Email entered is already registered", HttpStatus.FORBIDDEN);
        }

        return null;
    }

    @Override
    public ResponseEntity<?> registerClient(RegisterDto registerDto) {

        ResponseEntity<?> validatorResponse = registerFieldsValidator(registerDto.firstName(), registerDto.lastName(), registerDto.email(), registerDto.password());
        if(validatorResponse != null){
            return validatorResponse;
        }

        Client newClient = createClient(registerDto.firstName(), registerDto.lastName(), registerDto.email(), registerDto.password());
        Account defaultAccount = accountService.makeNewAccount();
        addNewAccountToNewClient(newClient, defaultAccount);

        saveNewClient(newClient);
        accountService.saveAccountRepository(defaultAccount);

        return new ResponseEntity<>("Client created, please log in", HttpStatus.CREATED);
    }
}
