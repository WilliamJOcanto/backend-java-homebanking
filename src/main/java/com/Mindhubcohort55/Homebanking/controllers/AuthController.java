package com.Mindhubcohort55.Homebanking.controllers;

import com.Mindhubcohort55.Homebanking.dtos.ClientDto;
import com.Mindhubcohort55.Homebanking.dtos.LoginDto;
import com.Mindhubcohort55.Homebanking.dtos.RegisterDto;
import com.Mindhubcohort55.Homebanking.services.AuthorizationService;
import com.Mindhubcohort55.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
        try {
            return authorizationService.authenticateAndMakeToken(loginDto);
        }
        catch (BadCredentialsException e) {
            return new ResponseEntity<>("You have entered an incorrect email or password", HttpStatus.UNAUTHORIZED);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred during authentication", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto){
        try{
            return authorizationService.registerClient(registerDto);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Error creating client: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getClient(Authentication authentication){
        return ResponseEntity.ok(new ClientDto(clientService.getClientByEmail(authentication)));
    }
}
