package com.uce.sedral.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtExpirationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtExpirationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.toLowerCase().startsWith("bearer ")) {
            String token = authHeader.substring(7);
            try {
                var jwt = jwtDecoder.decode(token);
            } catch (org.springframework.security.oauth2.jwt.JwtValidationException e) {
                boolean isExpired = e.getErrors().stream()
                        .anyMatch(error -> error.getDescription() != null &&
                                error.getDescription().toLowerCase().contains("expired"));
                if (isExpired) {
                    sendTokenExpiredResponse(request, response);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendTokenExpiredResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("fechaActual", new Date());
        errorResponse.put("mensaje", "Token JWT expirado");
        errorResponse.put("detalles", "Su sesión ha caducado. Por favor, inicie sesión nuevamente");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/v1/auth/") ||
                path.startsWith("/v1/debug/") ||
                path.startsWith("/swagger-ui/") ||
                path.startsWith("/v1/api-docs/");
    }
}