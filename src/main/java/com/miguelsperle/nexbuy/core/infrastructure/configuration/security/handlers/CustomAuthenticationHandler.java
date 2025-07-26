package com.miguelsperle.nexbuy.core.infrastructure.configuration.security.handlers;

import com.miguelsperle.nexbuy.core.infrastructure.adapters.in.web.dtos.ErrorMessageResponse;
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
import java.util.Collections;

@Component
public class CustomAuthenticationHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        final ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(
                Collections.singletonList("Authentication is required to access this resource"), HttpStatus.UNAUTHORIZED.getReasonPhrase()
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final OutputStream responseStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(responseStream, errorMessageResponse);
        responseStream.flush();
    }
}
