
package com.sandrajavaschool.OnlineStore.security.controllers;


import com.sandrajavaschool.OnlineStore.authHandler.service.JWTServiceImpl;
import com.sandrajavaschool.OnlineStore.dao.IRoleDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import com.sandrajavaschool.OnlineStore.security.dto.AuthResponseDto;
import com.sandrajavaschool.OnlineStore.security.dto.LoginDto;
import com.sandrajavaschool.OnlineStore.security.dto.RegisterDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@RestController
@RequestMapping("/api/auth/")
@AllArgsConstructor
public class RestControllerAuth {

    private final AuthenticationManager authenticationManager;
    private final JWTServiceImpl jwtService;


    //Metodo para poder registrar usuarios con ROLE_USER (para admin es == pero cambiando a ROLE_ADMIN)


    /*
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        System.out.println("WE ARE INTO REGISTER CONTROLLER");

        if (userDao.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("The user already exists", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPass(passwordEncoder.encode(registerDto.getPass()));
        Role role = roleDao.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(role));
        userDao.save(user);

        return new ResponseEntity<>("Successful", HttpStatus.OK);

    }

    @PostMapping("registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody RegisterDto registerDto) {

        if (userDao.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("The user already exists", HttpStatus.BAD_REQUEST);
        }


        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPass(passwordEncoder.encode(registerDto.getPass()));
        Role role = roleDao.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singletonList(role));
        userDao.save(user);

        return new ResponseEntity<>("Successful", HttpStatus.OK);

    }

     */
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
