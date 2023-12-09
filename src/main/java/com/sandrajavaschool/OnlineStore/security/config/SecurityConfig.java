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


@Configuration // Indicates to the Spring container that this is a security configuration class during app initialization.
@EnableWebSecurity //Activates Spring Security for the web application
//Enables method-level security, allowing the use of annotations like @Secured and @PreAuthorize
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // , (opcional)
@RequiredArgsConstructor
public class SecurityConfig {


    private final BCryptPasswordEncoder passwordEncoder;

    private final LoginSuccessHandler loginSuccessHandler;

    private final JpaUserDetailsService userDetailsService;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JWTService jwtService;

    //Configures the user details service and password encoder for authentication.
    @Autowired
    public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    //Configures and provides the authentication manager as a bean.
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //Configures the security filter chain using HttpSecurity.
    //Defines authorization rules for various paths, both public and secured

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((authz) -> {
                    authz
                            .requestMatchers("/css/**", "/js/**", "/images/**",
                                    "/order/receipt/**",
                                    "/mainPage",
                                    "/create/**", "/api/auth/**",
                                    "/", "/api/login", "productsList",
                                    "/order/receiptControl",
                                    "/order/receipt/anonymous",
                                    "/order/load-products/**",
                                    "/uploads/**",
                                    "order/save/**",
                                    "/save").permitAll()

                            //secured Paths
                            .requestMatchers("/list").hasAnyRole("ADMIN")
                            .requestMatchers("/createProduct/**").hasAnyRole("ADMIN")
                            .requestMatchers("/editProduct/**").hasAnyRole("ADMIN")
                            .requestMatchers("/deleteProduct/**").hasAnyRole("ADMIN")
                            .requestMatchers("/category/**").hasRole("ADMIN")
                            .requestMatchers("/order/ordersList/**").hasRole("ADMIN")
                            .requestMatchers("/form/**").hasRole("ADMIN")
                            .requestMatchers("/create/**").hasRole("ADMIN")
                            .requestMatchers("/sales/**").hasRole("ADMIN")
                            .requestMatchers("/uploadFileForm/**").hasRole("ADMIN")



                            .anyRequest().authenticated();

                });

        //Configures form-based login
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

        // JWT Filters for authentication and authorization

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