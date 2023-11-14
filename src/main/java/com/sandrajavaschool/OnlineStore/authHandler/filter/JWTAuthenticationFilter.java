package com.sandrajavaschool.OnlineStore.authHandler.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
    }

    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

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

        String email = authResult.getName();

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .signWith(SECRET_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + 3600000 * 4))
                .compact();

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("message", String.format("Hello %s, you have login successfully", email));

        //lo transforma del tipo mapper a json
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));

        response.setStatus(200);
        response.setContentType("application/json");

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {

        Map<String, Object> body = new HashMap<String, Object>();
        body.put("message", "Authentication error: your email or password is incorrect");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");


    }
}
