package com.Mindhubcohort55.Homebanking.controllers;

import com.Mindhubcohort55.Homebanking.dtos.ClientDto;
import com.Mindhubcohort55.Homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/")
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return new ResponseEntity<>(clientService.getAllClientDto(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClientById(@PathVariable Long id) {
        return clientService.getClientDto(id);
    }
}

