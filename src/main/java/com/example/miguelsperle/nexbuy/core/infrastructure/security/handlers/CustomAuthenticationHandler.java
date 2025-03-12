package com.example.miguelsperle.nexbuy.core.infrastructure.security.handlers;

import com.example.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
public class CustomAuthenticationHandler implements AuthenticationEntryPoint {
    private static final String DEFAULT_AUTHENTICATION_ERROR_MESSAGE = "Authentication failed";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        final ErrorMessageResponse errorMessage =
                new ErrorMessageResponse(DEFAULT_AUTHENTICATION_ERROR_MESSAGE, HttpStatus.FORBIDDEN.value());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        final OutputStream responseStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(responseStream, errorMessage);
        responseStream.flush();
    }
}
