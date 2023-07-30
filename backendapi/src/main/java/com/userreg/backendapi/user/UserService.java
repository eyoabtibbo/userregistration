package com.userreg.backendapi.user;

import com.userreg.backendapi.registration.PasswordConstraintValidator;
import com.userreg.backendapi.registration.RegistrationRequest;
import jakarta.security.auth.message.callback.PasswordValidationCallback;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public String signUp (AppUser appUser) {
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

        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        userRepository.save(appUser);

        return "Signup Successful";
    }
}
