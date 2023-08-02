package com.userreg.backendapi.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignUpSuccess() {
        AppUser appUser = new AppUser("Sam", "James", "SJames", "Passw0rd$", UserRole.USER);

        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.empty());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("passwordEncoded");

        String result = userService.signUp(appUser, "Canada");

        assertEquals("Signup Successful, Welcome!", result);
    }

    @Test
    public void testUsernameAlreadyExists() {
        AppUser appUser = new AppUser("Sam", "James", "SJames", "Passw0rd$", UserRole.USER);

        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.of(appUser));

        assertThrows(IllegalStateException.class, () -> userService.signUp(appUser, "Canada"));
    }

    @Test
    public void testInvalidPassword() {
        AppUser appUser = new AppUser("Sam", "James", "samJ", "passowrd", UserRole.USER);

        assertThrows(IllegalArgumentException.class, () -> userService.signUp(appUser, "Canada"));
    }
}
