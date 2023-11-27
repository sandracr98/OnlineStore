package com.sandrajavaschool.OnlineStore.security.config;


import com.sandrajavaschool.OnlineStore.authHandler.LoginSuccessHandler;
import com.sandrajavaschool.OnlineStore.authHandler.filter.JWTAuthenticationFilter;
import com.sandrajavaschool.OnlineStore.authHandler.filter.JWTAuthorizationFilter;
import com.sandrajavaschool.OnlineStore.authHandler.service.JWTService;
import com.sandrajavaschool.OnlineStore.security.service.JpaUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration //le indica al contenedor de spring que esta es una clase de seguridad en el momento de iniciar la app
@EnableWebSecurity
//Se activa la seguridad web en nuestra app, y esta clase contiene toda la config referente a la seguridad
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // , (opcional)
@RequiredArgsConstructor
public class SecurityConfig {


    private final BCryptPasswordEncoder passwordEncoder;

    private final LoginSuccessHandler loginSuccessHandler;

    private final JpaUserDetailsService userDetailsService;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JWTService jwtService;


    @Autowired
    public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> {
                    authz
                            .requestMatchers("/css/**", "/js/**", "/images/**",
                                    "/order/receipt/**",
                                    "/create/**", "/api/auth/**",
                                    "/", "/api/login", "productsList",
                                    "/order/receiptControl",
                                    "/order/receipt/anonymous",
                                    "/order/load-products/**",
                                    "/save").permitAll()

                            //aqui van las rutas privadas
                            .requestMatchers("/list").hasAnyRole("ADMIN")
                            .requestMatchers("/createProduct/**").hasAnyRole("ADMIN")
                            .requestMatchers("/editProduct/**").hasAnyRole("ADMIN")
                            .requestMatchers("/deleteProduct/**").hasAnyRole("ADMIN")
                            .requestMatchers("/category/**").hasRole("ADMIN")
                            .requestMatchers("/order/ordersList/**").hasRole("ADMIN")
                            .requestMatchers("/form/**").hasRole("ADMIN")
                            .requestMatchers("/create/**").hasRole("ADMIN")
                            .requestMatchers("/sales/**").hasRole("ADMIN")


                            .anyRequest().authenticated();

                });

        http.formLogin((form) -> form.loginPage("/api/login")
                .usernameParameter("email")
                .passwordParameter("pass")
                .loginProcessingUrl("/api/auth/login")
                .defaultSuccessUrl("/productsList", true)
                .successHandler(loginSuccessHandler)
                .permitAll());

        http.logout((logout) -> logout.permitAll()
                .logoutSuccessUrl("/"));

        http.exceptionHandling().accessDeniedPage("/error/error403");

        http
                .addFilter(new JWTAuthenticationFilter(authenticationConfiguration.getAuthenticationManager(), jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService));

        http
                .csrf().disable();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS); //desabilitamos el uso de sesion


        return http.build();

    }

}