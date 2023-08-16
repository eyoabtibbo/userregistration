package com.userreg.backendapi.user;

import com.userreg.backendapi.registration.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTestNegative {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private RegistrationService registrationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
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

    @Test
    public void testGetCountryFail() {
        String ipAddress = "31.156.170.203"; //Italy

        String country = registrationService.getCountry(ipAddress);
        assertNotEquals("Canada", country);

    }
}
