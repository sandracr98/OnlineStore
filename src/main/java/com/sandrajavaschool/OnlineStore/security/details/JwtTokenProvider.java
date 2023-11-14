/*package com.sandrajavaschool.OnlineStore.security.details;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //metodo para generar el token por medio de la autenticacion


    public String tokenProvider(Authentication authentication) {

        String email = authentication.getName();
        Date actualTime = new Date();

        //tiempo que va a durar nuestro token
        Date tokenExpiration = new Date(actualTime.getTime() + TokenConstants.JWT_EXPIRATION_TOKEN);

        //Linea para generar el token
        String token = Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(tokenExpiration)
                .signWith(SignatureAlgorithm.HS512, TokenConstants.JWT_SECRET) //para validar que el token no ha sido modificado
                .compact();

        return token;
    }

    //metodo de extraer un email a traves de un token

    public String getEmailFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(TokenConstants.JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    //metodo para validar el token

    public Boolean tokenValidation(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(TokenConstants.JWT_SECRET).parseClaimsJws(token).getBody();
            Date expirationDate = claims.getExpiration();

            if (expirationDate != null && expirationDate.toInstant().isBefore(Instant.now())) {
                throw new JwtException("Jwt has expired");
            }

            return true;

        }catch (JwtException e) {
            throw new BadCredentialsException("Jwt is incorrect");
        }
    }



}
*/