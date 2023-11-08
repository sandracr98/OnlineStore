package com.sandrajavaschool.OnlineStore.security.service;

import com.sandrajavaschool.OnlineStore.dao.IUserDao;
import com.sandrajavaschool.OnlineStore.entities.Role;
import com.sandrajavaschool.OnlineStore.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("jpaUserDetailsService")
@Transactional
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {


    private final IUserDao userDao;
    private Logger logger = LoggerFactory.getLogger(JpaUserDetailsService.class);


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userDao.findUserByEmail(email);


        if (user == null) {
            logger.error("Login error: the user does not exist ");
            throw new UsernameNotFoundException("The User is not found.");
        }


        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Role role: user.getRoles()) {
            logger.info("Role: " .concat(role.getName()));
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        if (authorities.isEmpty()) {
            logger.error("Login error: the user with email: " + email + "does not have any role");
            throw new UsernameNotFoundException("Login error: the user with email: " + email + "does not have any role");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPass(), user.isEnabled(), true, true, true, authorities);
    }


}























///////////////////////////////////////////////////////////////////////
    /*
    //En esta clase nos vamos a traer al usuario junto con su username, pass etc y asi
    //comprobar que existe y está logeado

    private final IUserDao userDao;

    private final Logger logger= LoggerFactory.getLogger(JpaUserDetailsService.class);


    //Metodo para traer una lista de autoridades por medio de una lista de roles
    public Collection<GrantedAuthority> mapToAuthorities(List<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

    }

    //Metodo para traer un usuario con todos sus datos por medio de
    //su email
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // MyUserDetails userDetails = new MyUserDetails();

        User user = userDao.findUserByEmail(email);


        if (user == null) {
            logger.error("Login error: the user does not exist ");
            throw new UsernameNotFoundException("The User is not found.");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPass(), mapToAuthorities(user.getRoles()));

    }

        userDetails.setUsername(user.getEmail());
        userDetails.setPass(user.getPass());

        Set<GrantedAuthority> authorities = new HashSet<>();

        userDetails.setGrantedAuthorities(authorities);


        for (Role role: user.getRoles()) {
            logger.info("Role: " .concat(role.getName()));
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        if (authorities.isEmpty()) {
            logger.error("Login error: the user with email: " + email + "does not have any role");
            throw new UsernameNotFoundException("Login error: the user with email: " + email + "does not have any role");
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPass(), authorities);



    }

   /*
   private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    */




