package com.userreg.backendapi.registration;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.userreg.backendapi.user.AppUser;
import com.userreg.backendapi.user.UserRole;
import com.userreg.backendapi.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    public String register(RegistrationRequest request, String ipAddress) {
        String country = getCountry(ipAddress);
        return userService.signUp(new AppUser(
                request.getFirstName(),
                request.getLastName(),
                request.getUsername(),
                request.getPassword(),
                UserRole.USER
        ), country);
    }

    public String getCountry(String ipAdress){
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = "http://ip-api.com/json/" + ipAdress;
        try {
            String response = restTemplate.getForObject(apiUrl, String.class);
            JsonElement jsonElement = JsonParser.parseString(response);
            String country = jsonElement.getAsJsonObject().get("country").getAsString();
            return country;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
