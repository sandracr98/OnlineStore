
package com.sandrajavaschool.OnlineStore.security.controllers;


import com.sandrajavaschool.OnlineStore.authHandler.service.JWTServiceImpl;
import com.sandrajavaschool.OnlineStore.security.dto.AuthResponseDto;
import com.sandrajavaschool.OnlineStore.security.dto.LoginDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class RestControllerAuth {

    private final AuthenticationManager authenticationManager;
    private final JWTServiceImpl jwtService;

    //Método para poder logear un usuario y obtener un token

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPass()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtService.create(authentication);

            return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
