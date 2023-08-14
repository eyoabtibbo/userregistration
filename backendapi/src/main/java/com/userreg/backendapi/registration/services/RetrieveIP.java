package com.userreg.backendapi.registration.services;

import jakarta.servlet.http.HttpServletRequest;

public class RetrieveIP {

    private static final String [] headers = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP"
    };

    public static String getUserIP(HttpServletRequest request) {

        for (String header: headers) {
            String addresses = request.getHeader(header);
            if(addresses.isEmpty()) {
                continue;
            }
            String [] address = addresses.split("\\s*, \\s*");
            return address[0];
        }
        return request.getRemoteAddr();
    }
}
