package com.sandrajavaschool.OnlineStore.authHandler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.Collection;

/**
 * Service interface for handling JSON Web Tokens (JWT) in the authentication process.
 */
public interface JWTService {

    /**
     * Creates a JWT token based on the provided authentication information.
     *
     * @param auth The authentication object.
     * @return The generated JWT token.
     * @throws IOException If an error occurs during token creation.
     */
    String create(Authentication auth) throws IOException;

    /**
     * Validates the given JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    boolean validate(String token);

    /**
     * Retrieves the claims from the provided JWT token.
     *
     * @param token The JWT token.
     * @return The claims extracted from the token.
     */
    Claims getClaims(String token);

    /**
     * Retrieves the email from the provided JWT token.
     *
     * @param token The JWT token.
     * @return The email extracted from the token.
     */
    String getEmail(String token);

    /**
     * Retrieves the roles (GrantedAuthorities) from the provided JWT token.
     *
     * @param token The JWT token.
     * @return The roles (GrantedAuthorities) extracted from the token.
     * @throws IOException If an error occurs during role extraction.
     */
    Collection<? extends GrantedAuthority> getRoles(String token) throws IOException;

    /**
     * Resolves the original token from the provided token (if it's a refresh token, for example).
     *
     * @param token The JWT token to resolve.
     * @return The resolved original token.
     */
    String resolve(String token);


}
