package com.plotfail.plotfailbe.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plotfail.plotfailbe.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("X-Flag-Unauthorized", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error("UNAUTHORIZED")
                .status(HttpStatus.UNAUTHORIZED.value())
                .path(request.getRequestURI())
                .message("Authentication required")
                .build();

        new ObjectMapper().writeValue(response.getWriter(), errorResponse);
    }
}
