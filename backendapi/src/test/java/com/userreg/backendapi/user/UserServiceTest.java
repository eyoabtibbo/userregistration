package com.userreg.backendapi.user;

import com.userreg.backendapi.registration.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private RestTemplate restTemplate;

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

    @Test
    public void testGetCountry() {
        //String mockCountry = "{\"country\":\"Canada\"}";
        String ipAddress = "192.206.151.131"; //192.206.151.131 31.156.170.203

        //when(restTemplate.getForObject("http://ip-api.com/json/" + ipAddress, String.class)).thenReturn(mockCountry);

        String country = registrationService.getCountry(ipAddress);
        assertEquals("Canada", country);

    }
}
