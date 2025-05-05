package com.miguelsperle.nexbuy.core.infrastructure.security.handlers;

import com.miguelsperle.nexbuy.core.infrastructure.dtos.ErrorMessageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final String DEFAULT_ACCESS_DENIED_MESSAGE = "Access denied. You don't have permission to access this resource";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        final ErrorMessageResponse errorMessageResponse = new ErrorMessageResponse(
                List.of(DEFAULT_ACCESS_DENIED_MESSAGE), HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value()
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        final OutputStream responseStream = response.getOutputStream();
        final ObjectMapper mapper = new ObjectMapper();

        mapper.writeValue(responseStream, errorMessageResponse);
        responseStream.flush();
    }
}