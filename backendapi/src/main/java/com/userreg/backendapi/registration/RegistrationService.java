package com.userreg.backendapi.registration;

import com.userreg.backendapi.user.AppUser;
import com.userreg.backendapi.user.UserRole;
import com.userreg.backendapi.user.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    public String register(RegistrationRequest request) {
        return userService.signUp(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getPassword(),
                UserRole.USER
        ));
    }
}
