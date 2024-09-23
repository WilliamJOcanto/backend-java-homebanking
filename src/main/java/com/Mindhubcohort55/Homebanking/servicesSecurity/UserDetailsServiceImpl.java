package com.Mindhubcohort55.Homebanking.servicesSecurity;

import com.Mindhubcohort55.Homebanking.models.Client;
import com.Mindhubcohort55.Homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Client client = clientRepository.findByEmail(username);

    if(client == null){
        throw new UsernameNotFoundException(username);
    }

    if(username.contains("admin")){
        return User
                .withUsername(username)
                .password(client.getPassword())
                .roles("ADMIN")
                .build();
    }

        return User
                .withUsername(username)
                .password(client.getPassword())
                .roles("CLIENT")
                .build();
    }
}
