package com.uce.sedral.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthException implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        Throwable currentCause = authException;
        int depth = 0;
        while (currentCause != null && depth < 10) {
            currentCause = currentCause.getCause();
            depth++;
        }

        String errorMessage = getCustomErrorMessage(authException);
        int statusCode = HttpServletResponse.SC_UNAUTHORIZED;

        final Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("fechaActual", new Date());
        errorResponse.put("status", statusCode);
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", errorMessage);
        errorResponse.put("path", request.getServletPath());

        errorResponse.put("exception", authException.getMessage());
        errorResponse.put("exceptionType", authException.getClass().getSimpleName());

        response.setContentType("application/json");
        response.setStatus(statusCode);
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }

    private String getCustomErrorMessage(AuthenticationException exception) {
        if (exception.getMessage() != null) {
            if (exception.getMessage().toLowerCase().contains("bad credentials")) {
                return "Credenciales incorrectas";
            }
            if (exception.getMessage().toLowerCase().contains("full authentication")) {
                return "Se requiere autenticación completa";
            }
        }
        return "No está autorizado para acceder a este recurso";
    }
}