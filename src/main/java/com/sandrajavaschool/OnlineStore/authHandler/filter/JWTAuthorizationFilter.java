package com.sandrajavaschool.OnlineStore.authHandler.filter;

import com.sandrajavaschool.OnlineStore.authHandler.service.JWTService;

import com.sandrajavaschool.OnlineStore.authHandler.service.JWTServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private JWTService jwtService;
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        super(authenticationManager);
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {


        // Extract the JWT token from the request header
        String header = request.getHeader(JWTServiceImpl.HEADER_STRING);

        // Check if authentication is not required
        if (!requiresAuthentication(header)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;

        // Validate the JWT token using the jwtService
        if (jwtService.validate(header)) {

            // If the token is valid, create an authentication token with user details
            authentication = new UsernamePasswordAuthenticationToken(jwtService.getEmail(header),
                    null, jwtService.getRoles(header));
        }

        // Authenticate the user within the request (since it doesn't handle sessions)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);

    }

    protected boolean requiresAuthentication(String header) {

        // Check if the header is null or doesn't start with the expected token prefix
        if (header == null || !header.startsWith(JWTServiceImpl.TOKEN_PREFIX)) {
            return false;
        }
        // Authentication is required if the header is not null and starts with
        // the expected token prefix
        return true;
    }

}
