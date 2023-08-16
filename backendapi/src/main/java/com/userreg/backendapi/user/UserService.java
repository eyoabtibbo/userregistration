package com.userreg.backendapi.user;

import com.userreg.backendapi.registration.PasswordConstraintValidator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public String signUp (AppUser appUser, String country) {
        if(appUser.getUsername().isEmpty()) {
            throw new IllegalStateException("username cannot be empty");
        }

        boolean userExists = userRepository.findByUsername(appUser.getUsername()).isPresent();
        if(userExists) {
            throw new IllegalStateException("username already exists");
        }

        PasswordConstraintValidator passwordConstraintValidator = new PasswordConstraintValidator();
        if (!passwordConstraintValidator.isValid(appUser.getPassword(),null )) {
            throw new IllegalArgumentException("Invalid password.");
        }

        if (!country.equals("Canada")) {
            throw new IllegalStateException("You are not eligible to register as you are not located in Canada");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        userRepository.save(appUser);

        UUID uuid = UUID.randomUUID();
        return "Signup Successful, Welcome! " + uuid;
    }
}
