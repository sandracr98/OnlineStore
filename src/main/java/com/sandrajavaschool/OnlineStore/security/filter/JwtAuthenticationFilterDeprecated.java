/*
package com.sandrajavaschool.OnlineStore.security.filter;

import com.sandrajavaschool.OnlineStore.security.details.JwtTokenProvider;
import com.sandrajavaschool.OnlineStore.security.service.JpaUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


//Este filtro que se ejecuta en el request y antes del controler va a realizar el login y
//la autenticacion (un cliente envia las credenciales mediante una url por una peticion post)

//funcion: validar la informacion del token y si es exitoso, establece la autenticacion
//de un usuario en la solicitud que se está realizando. o en el contexto de seguridad de la app


public class JwtAuthenticationFilterDeprecated extends OncePerRequestFilter {

    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public JwtAuthenticationFilterDeprecated() {
    }

    private String getRequestToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){

            return bearerToken.substring(7, bearerToken.length());
        }

        return  null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("WE ARE INTO DOFILTER INTERNAL");

        String token = getRequestToken(request);

        System.out.println("TOKEN:" + token);

        if (StringUtils.hasText(token) && tokenProvider.tokenValidation(token)) {

            System.out.println("DENTRO DEL IF,");
            String email = tokenProvider.getEmailFromJwt(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            List<String> userRoles = userDetails
                    .getAuthorities().stream().
                    map(GrantedAuthority::getAuthority).
                    toList();

            if (userRoles.contains("USER") || userRoles.contains("ADMIN")) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken
                                (userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails
                        (new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


        }

        filterChain.doFilter(request, response);

    }




    //////////////////////////////////////////////////////////////

/*
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        String username = this.obtainUsername(request);
        username = username != null ? username.trim() : "";
        String password = this.obtainPassword(request);
        password = password != null ? password : "";

        if (username != null && password != null) {
            logger.info("Username from request parameter: " + username);
            logger.info("Password from request parameter: " + password);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);


        return authenticationManager.authenticate(authToken);
    }






    //////////////////////////////////////////////////////////////////

    /*
    private final JpaUserDetailsService userDetailsService;
    private final JwtTokenProvider tokenProvider;


    private String getRequestToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){

            return bearerToken.substring(7, bearerToken.length());
        }

        return  null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = getRequestToken(request);

        if (StringUtils.hasText(token) && tokenProvider.tokenValidation(token)) {
            String email = tokenProvider.getEmailFromJwt(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            List<String> userRoles = userDetails
                            .getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority).
                            toList();

            if (userRoles.contains("USER") || userRoles.contains("ADMIN")) {

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken
                                (userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails
                        (new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        filterChain.doFilter(request, response);

    }



}


        */
