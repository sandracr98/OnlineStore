package com.sandrajavaschool.OnlineStore.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//funcion: validar la informacion del token y si es exitoso, establece la autenticacion
//de un usuario en la solicitud que se está realizando. o en el contexto de seguridad de la app
/*
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

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

