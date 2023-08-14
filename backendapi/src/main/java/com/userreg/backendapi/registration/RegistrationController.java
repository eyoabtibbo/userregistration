package com.userreg.backendapi.registration;

import com.userreg.backendapi.registration.services.RegistrationService;
import com.userreg.backendapi.registration.services.RetrieveIP;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/registration")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request, BindingResult bindingResult, HttpServletRequest servletRequest) {
        String ipAddress = RetrieveIP.getUserIP(servletRequest);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage()); //bindingResult.getAllErrors().get(0).getDefaultMessage();
        }
        String response = registrationService.register(request, ipAddress);
        return ResponseEntity.ok(response);
    }
}
