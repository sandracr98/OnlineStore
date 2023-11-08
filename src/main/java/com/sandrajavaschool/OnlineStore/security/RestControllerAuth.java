package com.sandrajavaschool.OnlineStore.security;

import com.sandrajavaschool.OnlineStore.dao.IRoleDao;
import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.dto.RegisterDto;
import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
/*
@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class RestControllerAuth {

    //encriptamos contraseñas al crear un usuario y verificar si se logean

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRoleDao roleDao;
    private IUserDao userDao;
    private JwtTokenProvider tokenProvider;

    //Metodo para poder registrar usuarios con role user +++
    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody RegisterDto registerDto) {

        if (userDao.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("The email exists", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPass(passwordEncoder.encode(registerDto.getPass()));
        Role role = roleDao.findByName("USER").get();
        user.setRoles(Collections.singletonList(role));
        userDao.save(user);

        return new ResponseEntity<>("Successfull", HttpStatus.OK);
    }
}
*/
