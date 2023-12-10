package com.sandrajavaschool.OnlineStore.authHandler.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandrajavaschool.OnlineStore.authHandler.service.JWTService;
import com.sandrajavaschool.OnlineStore.authHandler.service.JWTServiceImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));

        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException, BadCredentialsException {

        // Extract email and password from request parameters
        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        if (email != null && pass != null) {

            logger.info("Email from request parameter (form-data): " + email);
            logger.info("Password from request parameter (form-data): " + pass);

        } else {

            User user = null;
            // When data is sent as JSON with the body in raw format and JSON content type (e.g., Postman)
            // Deserialize JSON into a User object

            try {

                user = new ObjectMapper().readValue(request.getInputStream(), User.class);

                // Extract email and password from the deserialized User object
                email = user.getUsername();
                pass = user.getPassword();

                logger.info("Email from request getInputStream (raw): " + email);
                logger.info("Password from request getInputStream (raw): " + pass);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Trim leading and trailing spaces from the email
        email = email.trim();

        // Create an authentication token with the extracted email and password
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, pass);

        // Use the authentication manager to authenticate the token
        return authenticationManager.authenticate(authToken);
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        // Create a JWT token based on the authentication result
        String token = jwtService.create(authResult);

        // Set the HTTP response status and content type
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");

        // Add the JWT token to the response header
        response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);

        // Create a body map to include in the response
        Map<String, Object> body = new HashMap<>();

        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("message", String.format("Hello %s, you have logged in successfully", authResult.getName()));

        // Write the response body as a JSON string
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Authentication error: your email or password is incorrect");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));


    }
}
