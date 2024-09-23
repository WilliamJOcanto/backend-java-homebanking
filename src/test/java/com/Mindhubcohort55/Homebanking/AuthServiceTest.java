//package com.Mindhubcohort55.Homebanking;
//
//import com.Mindhubcohort55.Homebanking.dtos.LoginDto;
//import com.Mindhubcohort55.Homebanking.dtos.RegisterDto;
//import com.Mindhubcohort55.Homebanking.models.Account;
//import com.Mindhubcohort55.Homebanking.models.Client;
//import com.Mindhubcohort55.Homebanking.repositories.ClientRepository;
//import com.Mindhubcohort55.Homebanking.services.AccountService;
//import com.Mindhubcohort55.Homebanking.services.AuthorizationService;
//import com.Mindhubcohort55.Homebanking.servicesSecurity.JwtUtilService;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//public class AuthServiceTest {
//
//    @Autowired
//    private AuthorizationService authorizationService;
//
//    @MockBean
//    private AccountService accountService;
//
//    @MockBean
//    private ClientRepository clientRepository;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @MockBean
//    private UserDetailsService userDetailsService;
//
//    @MockBean
//    private JwtUtilService jwtUtilService;
//
//    @Test
//    public void testTextFieldValidator() {
//        assertThat(authorizationService.textFieldValidator(" "), is(true));
//        assertThat(authorizationService.textFieldValidator("text"), is(false));
//
//    }
//
//    @Test
//    public void testAuthenticateUser() {
//        Authentication auth = mock(Authentication.class);
//        when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
//
//        Authentication result = authorizationService.authenticateUser("user", "123");
//        assertThat(result, is(notNullValue()));
//    }
//
//    @Test
//    public void testGetUserDetails() {
//        UserDetails userDetails = mock(UserDetails.class);
//        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
//
//        UserDetails result = authorizationService.getUserDetails("user");
//        assertThat(result, is(notNullValue()));
//    }
//
//    @Test
//    public void testGenerateNewJwt() {
//        UserDetails userDetails = mock(UserDetails.class);
//        when(jwtUtilService.generateToken(userDetails)).thenReturn("token");
//
//        String jwt = authorizationService.generateNewJwt(userDetails);
//        assertThat(jwt, is(equalTo("token")));
//    }
//
//    @Test
//    public void testLoginFieldsValidator() {
//        ResponseEntity<?> responseFieldValidator = authorizationService.loginFieldsValidator("", "password");
//        assertThat(responseFieldValidator.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
//
//        responseFieldValidator = authorizationService.loginFieldsValidator("email@example.com", "");
//        assertThat(responseFieldValidator.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
//
//        responseFieldValidator = authorizationService.loginFieldsValidator("email@example.com", "password");
//        assertThat(responseFieldValidator, is(nullValue()));
//    }
//
//    @Test
//    public void testAuthenticateAndMakeToken() {
//        LoginDto loginDto = new LoginDto("email@example.com", "password");
//
//        Authentication auth = mock(Authentication.class);
//        UserDetails userDetails = mock(UserDetails.class);
//        when(authenticationManager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
//        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
//        when(jwtUtilService.generateToken(userDetails)).thenReturn("jwtToken");
//
//        ResponseEntity<?> response = authorizationService.authenticateAndMakeToken(loginDto);
//        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));
//        assertThat(response.getBody(), is(equalTo("jwtToken")));
//    }
//
//    @Test
//    public void testIsClientExists() {
//        when(clientRepository.existsByEmail(anyString())).thenReturn(true);
//        assertThat(authorizationService.isClientExists("email@example.com"), is(true));
//    }
//
//    @Test
//    public void testCreateClient() {
//        Client client = authorizationService.createClient("Will", "Ocanto", "email@example.com", "password");
//        assertThat(client, is(notNullValue()));
//        assertThat(client.getFirstName(), is(equalTo("Will")));
//        assertThat(client.getLastName(), is(equalTo("Ocanto")));
//    }
//
//    @Test
//    public void testSaveNewClient() {
//        Client client = new Client();
//        authorizationService.saveNewClient(client);
//        verify(clientRepository, times(1)).save(client);
//    }
//
//    @Test
//    public void testAddNewAccountToNewClient() {
//        Client client = new Client();
//        Account account = new Account();
//        authorizationService.addNewAccountToNewClient(client, account);
//        assertThat(client.getAccounts(), is(contains(account)));
//    }
//
//    @Test
//    public void testRegisterFieldsValidator() {
//        ResponseEntity<?> responseFieldValidator = authorizationService.registerFieldsValidator("", "Ocanto", "email@example.com", "password");
//        assertThat(responseFieldValidator.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
//        assertThat(responseFieldValidator.getBody(), is(equalTo("The Name field must noy be empty")));
//
//        responseFieldValidator = authorizationService.registerFieldsValidator("Will", "", "email@example.com", "password");
//        assertThat(responseFieldValidator.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
//        assertThat(responseFieldValidator.getBody(), is(equalTo("The Last Name field must not be empty")));
//
//        responseFieldValidator = authorizationService.registerFieldsValidator("Will", "Ocanto", "", "password");
//        assertThat(responseFieldValidator.getStatusCode(), is(equalTo(HttpStatus.FORBIDDEN)));
//        assertThat(responseFieldValidator.getBody(), is(equalTo("The Email field must not be empty")));
//
//        responseFieldValidator = authorizationService.registerFieldsValidator("Will", "Ocanto", "email@example.com", "");
//        assertThat(responseFieldValidator.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
//        assertThat(responseFieldValidator.getBody(), is(equalTo("The Password field must not be empty")));
//
//        when(clientRepository.existsByEmail(anyString())).thenReturn(true);
//        responseFieldValidator = authorizationService.registerFieldsValidator("Will", "Ocanto", "email@example.com", "password");
//        assertThat(responseFieldValidator.getStatusCode(), is(equalTo(HttpStatus.FORBIDDEN)));
//        assertThat(responseFieldValidator.getBody(), is(equalTo("The Email entered is already registered")));
//
//        when(clientRepository.existsByEmail(anyString())).thenReturn(false);
//        responseFieldValidator = authorizationService.registerFieldsValidator("Will", "Ocanto", "email@example.com", "password");
//        assertThat(responseFieldValidator, is(nullValue()));
//    }
//
//    @Test
//    public void testRegisterClient() {
//        RegisterDto registerDto = new RegisterDto("Will", "Ocanto", "email@example.com", "password");
//        Client client = new Client();
//        Account defaultAccount = new Account();
//
//        when(accountService.makeNewAccount()).thenReturn(defaultAccount);
//        when(clientRepository.existsByEmail(anyString())).thenReturn(false);
//
//        ResponseEntity<?> response = authorizationService.registerClient(registerDto);
//        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.CREATED)));
//    }
//}
