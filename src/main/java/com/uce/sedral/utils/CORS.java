package com.uce.sedral.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORS implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpServletRequest servletRequest = (HttpServletRequest) request;

        servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        servletResponse.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, PATCH, POST, PUT");
        servletResponse.setHeader("Access-Control-Max-Age", "3600");
        servletResponse.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");

        if ("OPTIONS".equalsIgnoreCase(servletRequest.getMethod())) {
            servletResponse.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
