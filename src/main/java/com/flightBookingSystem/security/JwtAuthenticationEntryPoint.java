package com.flightBookingSystem.security;

import java.io.IOException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        String message = "Unauthorized request";

        if (authException.getCause() instanceof ExpiredJwtException) {
            message = "Token has expired";
        } else if (authException.getCause() instanceof MalformedJwtException) {
            message = "Malformed JWT token";
        } else if (authException.getCause() instanceof SignatureException) {
            message = "Invalid JWT signature";
        } else if (authException instanceof InsufficientAuthenticationException) {
            message = "Authentication failed: Insufficient credentials";
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

}