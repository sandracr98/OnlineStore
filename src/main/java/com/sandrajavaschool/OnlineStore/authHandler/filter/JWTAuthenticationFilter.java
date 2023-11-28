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

        String email = request.getParameter("email");
        String pass = request.getParameter("pass");

        if (email != null && pass != null) {

            logger.info("Email desde request parameter (form-data): " + email);
            logger.info("Password desde request parameter (form-data): " + pass);

        } else {

            User user = null;
            //cuando se envian los datos tipo json con el body en raw y formato json (postman)
            //es a la inversa convertimos un json en un objeto

            try {

                user = new ObjectMapper().readValue(request.getInputStream(), User.class);

                email = user.getUsername();
                pass = user.getPassword();

                logger.info("Email desde request getInputStream (raw): " + email);
                logger.info("Password desde request getInputStream (raw): " + pass);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        email = email.trim();

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(email, pass);

        return authenticationManager.authenticate(authToken);
    }


    //el auth de aqui ya esta autenticado con el user los roles etc
    //ahora vamos a crear nuestro token
    //nota: principal === user
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        String token = jwtService.create(authResult);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("message", String.format("Hello %s, you have logged in successfully", authResult.getName()));

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
