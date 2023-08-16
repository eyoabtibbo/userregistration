package com.userreg.backendapi.user;

import com.userreg.backendapi.registration.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignUpSuccess() {
        //Will need to comment out UUID for test to pass
        AppUser appUser = new AppUser("Sam", "James", "SJames", "Passw0rd$", UserRole.USER);

        when(userRepository.findByUsername(anyString())).thenReturn(java.util.Optional.empty());
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("passwordEncoded");

        String result = userService.signUp(appUser, "Canada");

        assertTrue(result.contains("Signup Successful, Welcome!"));
    }

    @Test
    public void testGetCountry() {
        String ipAddress = "192.206.151.131"; //Canada

        String country = registrationService.getCountry(ipAddress);
        assertEquals("Canada", country);

    }
}
