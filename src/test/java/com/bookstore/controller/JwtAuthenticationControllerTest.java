package com.bookstore.controller;

import com.bookstore.config.JwtTokenUtil;
import com.bookstore.exceptions.AuthenticationException;
import com.bookstore.model.JwtRequest;
import com.bookstore.model.UserDTO;
import com.bookstore.service.JwtUserDetailsService;
import com.bookstore.user.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationControllerTest {

    private static final String USER_NAME = "user";
    private static final String PASSWORD = "password";
    private static final String USER_DISABLED_MESSAGE = "User " + USER_NAME + " is disabled";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Provided username and password are invalid.";

    @Mock
    private JwtRequest mockAuthenticationRequest;

    @Mock
    private UserDTO mockUserDTO;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtUserDetailsService userDetailsService;

    private JwtAuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        authenticationController = new JwtAuthenticationController(
                authenticationManager,
                jwtTokenUtil,
                userDetailsService
        );
    }

    @Test
    void createAuthenticationTokenForUser() throws Exception {
        //given
        //when
        ResponseEntity response = authenticationController.createAuthenticationToken(mockAuthenticationRequest);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verifyMockAuthenticationRequestCalls();
    }

    @Test
    void handleBadCredentialsExceptionOnAuthenticate() {
        //given
        //when
        try {
            authenticationController.createAuthenticationToken(mockAuthenticationRequest);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(AuthenticationException.class);
            assertThat(exception.getMessage()).isEqualTo(INVALID_CREDENTIALS_MESSAGE);
        }
        //then
        verifyMockAuthenticationRequestCalls();
    }

    @Test
    void handleDisabledExceptionOnAuthenticate() {
        //given
        when(mockAuthenticationRequest.getUsername()).thenReturn(USER_NAME);
        when(mockAuthenticationRequest.getPassword()).thenReturn(PASSWORD);
        //when
        try {
            authenticationController.createAuthenticationToken(mockAuthenticationRequest);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(AuthenticationException.class);
            assertThat(exception.getMessage()).isEqualTo(USER_DISABLED_MESSAGE);
        }
        //then
        verifyMockAuthenticationRequestCalls();
    }

    @Test
    void saveUserInDB() {
        //given
        when(userDetailsService.save(mockUserDTO)).thenReturn(new UserDAO());
        //when
        ResponseEntity response = authenticationController.saveUser(mockUserDTO);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private void verifyMockAuthenticationRequestCalls() {
        verify(mockAuthenticationRequest, times(2)).getUsername();
        verify(mockAuthenticationRequest).getPassword();
    }
}
