package com.sandrajavaschool.OnlineStore.security;
/*
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
/*
@Component
public class JwtTokenProvider {

    //metodo para generar el token por medio de la autenticacion

    public String tokenProvider(Authentication authentication) {

        String email = authentication.getName();
        Date actualTime = new Date();

        //tiempo que va a durar nuestro token
        Date tokenExpiration = new Date(actualTime.getTime() + TokenConstants.EXPIRATION_TOKEN);

        //Linea para generar el token
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(tokenExpiration)
                .signWith(SignatureAlgorithm.HS512, TokenConstants.SECRET) //para validar que el token no ha sido modificado
                .compact();

        return token;
    }

    //metodo de extraer un email a traves de un token

    public String getEmailFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(TokenConstants.SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    //metodo para validar el token
    public Boolean tokenValidation(String token) {
        try {
            Jwts.parser().setSigningKey(TokenConstants.SECRET).parseClaimsJws(token);
            return true;

        }catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Jwt is expired or is incorrect");
        }
    }

}*/
