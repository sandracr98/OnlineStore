package com.sandrajavaschool.OnlineStore.authHandler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandrajavaschool.OnlineStore.authHandler.filter.SimpleGrantedAuthorityMixin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTServiceImpl implements JWTService{

    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public static final long EXPIRATION_DATE = 14000000L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";


    // The method takes an Authentication object (auth) as a parameter.
    // This object typically contains details about the authenticated user, such as username and authorities (roles).
    @Override
    public String create(Authentication auth) throws IOException {

        // Extract user email from the authentication object
        String email = auth.getName();

        // Extract user roles from the authentication object
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        // Create a JWT Claims object and set user roles as authorities
        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        // Build a JWT token with user email as the subject, sign it with the secret key,
        // set expiration time, and compact it into a string
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .signWith(SECRET_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_DATE))
                .compact();

        return token;
    }

    @Override
    public boolean validate(String token) {

        try {
            // Attempt to retrieve claims from the provided JWT token
            getClaims(token);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            // If an exception occurs during the validation (e.g., token is invalid or malformed),
            // catch the exception and return false indicating that the token is not valid
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {

        // Use the JWT parser to extract and decode the claims from the provided token
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)  // Set the secret key for verification
                .parseClaimsJws(resolve(token))  // Parse the token and retrieve the JWS (JSON Web Signature), bearer is removed here
                .getBody();  // Extract the claims from the JWS body
        return claims;
    }

    @Override
    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {

        // Extract the "authorities" claim from the decoded claims of the token
        Object roles = getClaims(token).get("authorities");

        // Use Jackson's ObjectMapper to map the roles from a JSON string to a collection of GrantedAuthority
        Collection<? extends GrantedAuthority> authorities = Arrays
                .asList(new ObjectMapper()
                        .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                        .readValue(roles.toString()
                                        .getBytes(),
                                SimpleGrantedAuthority[].class));

        // Return the collection of GrantedAuthority representing the roles
        return authorities;
    }

    @Override
    public String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)){
            return token.replace(TOKEN_PREFIX, "");
        }
        return null;
    }
}
