package com.sandrajavaschool.OnlineStore.controllers;

import com.sandrajavaschool.OnlineStore.authHandler.service.JWTServiceImpl;
import com.sandrajavaschool.OnlineStore.security.controllers.RestControllerAuth;
import com.sandrajavaschool.OnlineStore.security.dto.AuthResponseDto;
import com.sandrajavaschool.OnlineStore.security.dto.LoginDto;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class AuthControllerTest {

    @InjectMocks
    private RestControllerAuth authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTServiceImpl jwtService;

    @Test
    public void testLogin() throws IOException {
        MockitoAnnotations.initMocks(this);

        LoginDto loginDto = new LoginDto();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPass());
        Authentication mockAuthentication = mock(Authentication.class);
        when(authenticationManager.authenticate(authRequest)).thenReturn(mockAuthentication);

        String mockToken = "mockToken";
        when(jwtService.create(mockAuthentication)).thenReturn(mockToken);

        ResponseEntity<AuthResponseDto> responseEntity = authController.login(loginDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(authenticationManager).authenticate(authRequest);

        verify(jwtService).create(mockAuthentication);
    }
}
