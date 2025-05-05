package com.miguelsperle.nexbuy.core.infrastructure.security.handlers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
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
import java.util.List;

@Component
public class CustomAuthenticationHandler implements AuthenticationEntryPoint {
    private static final String DEFAULT_AUTHENTICATION_ERROR_MESSAGE = "Authentication is required to access this resource";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        final ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(
                List.of(DEFAULT_AUTHENTICATION_ERROR_MESSAGE), HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED.value()
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final OutputStream responseStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(responseStream, errorMessageResponse);
        responseStream.flush();
    }
}
