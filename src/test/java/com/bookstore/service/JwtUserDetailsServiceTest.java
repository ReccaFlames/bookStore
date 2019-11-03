package com.bookstore.service;

import com.bookstore.model.UserDTO;
import com.bookstore.user.UserDAO;
import com.bookstore.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    private static final String USER_NAME = "user";
    private static final String UNKNOWN_USER = "unknownUser";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found with username: ";

    @Mock
    private UserDTO mockUserDTO;

    @Mock
    private UserDAO mockUserDAO;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder bcryptEncoder;

    @InjectMocks
    private JwtUserDetailsService userDetailsService;

    @Test
    void loadUserByName() {
        //given
        when(userRepository.findByUsername(anyString())).thenReturn(mockUserDAO);
        when(mockUserDAO.getUsername()).thenReturn(USER_NAME);
        when(mockUserDAO.getPassword()).thenReturn(PASSWORD);
        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(USER_NAME);
        //then
        verify(userRepository).findByUsername(anyString());
        assertThat(userDetails).isNotNull();
    }

    @Test
    void throwExceptionForUserNotFound() {
        //given
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        //when
        try {
            userDetailsService.loadUserByUsername(UNKNOWN_USER);
        } catch (Exception exception) {
            assertThat(exception).isInstanceOf(UsernameNotFoundException.class);
            assertThat(exception.getMessage()).isEqualTo(USER_NOT_FOUND_MESSAGE + UNKNOWN_USER);
        }
        //then
        verify(userRepository).findByUsername(anyString());
    }

    @Test
    void saveUserInDB() {
        //given
        when(mockUserDTO.getUsername()).thenReturn(USER_NAME);
        when(mockUserDTO.getPassword()).thenReturn(PASSWORD);
        when(bcryptEncoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any(UserDAO.class))).thenReturn(mockUserDAO);
        //when
        UserDAO userDAO = userDetailsService.save(mockUserDTO);
        //then
        assertThat(userDAO).isNotNull();
        verify(userRepository).save(any(UserDAO.class));
        verify(bcryptEncoder).encode(PASSWORD);
    }
}
