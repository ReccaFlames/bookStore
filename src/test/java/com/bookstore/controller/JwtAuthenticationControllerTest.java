package com.bookstore.controller;

import com.bookstore.config.JwtTokenUtil;
import com.bookstore.exceptions.AuthenticationException;
import com.bookstore.model.JwtRequest;
import com.bookstore.model.UserDTO;
import com.bookstore.service.JwtUserDetailsService;
import com.bookstore.user.UserDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JwtAuthenticationControllerTest {

    private static final String USER_NAME = "user";
    private static final String PASSWORD = "password";
    private static final String USER_DISABLED_MESSAGE = "User " + USER_NAME + " is disabled";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Provided username and password are invalid.";
    private static final String TOKEN = "token";

    @Mock
    private JwtRequest mockAuthenticationRequest;

    @Mock
    private UserDetails mockUserDetails;

    @Mock
    private UserDTO mockUserDTO;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtUserDetailsService userDetailsService;

    private JwtAuthenticationController authenticationController;

    @Before
    public void setUp() {
        when(mockAuthenticationRequest.getUsername()).thenReturn(USER_NAME);
        when(mockAuthenticationRequest.getPassword()).thenReturn(PASSWORD);
        authenticationController = new JwtAuthenticationController(
                authenticationManager,
                jwtTokenUtil,
                userDetailsService
        );
    }

    @Test
    public void createAuthenticationTokenForUser() throws Exception {
        //given
        when(userDetailsService.loadUserByUsername(USER_NAME)).thenReturn(mockUserDetails);
        when(jwtTokenUtil.generateToken(mockUserDetails)).thenReturn(TOKEN);
        //when
        ResponseEntity response = authenticationController.createAuthenticationToken(mockAuthenticationRequest);
        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verifyMockAuthenticationRequestCalls();
    }

    @Test
    public void handleBadCredentialsExceptionOnAuthenticate() {
        //given
        when(authenticationManager.authenticate(mock(Authentication.class))).thenThrow(new BadCredentialsException("INVALID_CREDENTIALS"));
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
    public void handleDisabledExceptionOnAuthenticate() {
        //given
        when(authenticationManager.authenticate(mock(Authentication.class))).thenThrow(new DisabledException("USER_DISABLED"));
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
    public void saveUserInDB() {
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
