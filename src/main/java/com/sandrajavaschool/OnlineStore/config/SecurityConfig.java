package com.sandrajavaschool.OnlineStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Configuramos el Metodo HTTPSecurity para indicar la cadena de filtros
    // de Autotización que vamos a seguir;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                    try {
                        //aqui van las estaticas, acceso a todos los recursos estaticos
                        //y permitimos el acceso a todos
                        authz.requestMatchers("/css/**", "/js/**", "/images/**", "/product/productsList").permitAll()
                                //aqui van las rutas privadas
                                .requestMatchers("/list").hasAnyRole("ADMIN")
                                .requestMatchers("/createProduct/**").hasAnyRole("ADMIN")
                                .requestMatchers("/editProduct/**").hasAnyRole("ADMIN")
                                .requestMatchers("/deleteProduct/**").hasAnyRole("ADMIN")
                                .requestMatchers("/category/**").hasRole("ADMIN")
                                .requestMatchers("/order/orderList/**").hasRole("ADMIN")
                                .requestMatchers("/form/**").hasRole("ADMIN")
                                .anyRequest().authenticated();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        // Configuramos la página personalizada de inicio de sesión.
        // También la url donde se hará el Post recibido de manera automática por Springboot
        // Después, indicamos cuál es la url por defecto al hacer login
        // Además, permitimos el acceso a to do el mundo.

        http.formLogin((form) -> form.loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .permitAll());

        // Configuramos el sistema de cierre de sesión de la aplicación como el cierre de sesión predeterminado de Spring Security.

        http.logout((logout) -> logout.permitAll()
                .logoutSuccessUrl("/"));

        // Devolvemos el objeto HttpSecurity configurado para que Spring Boot y Spring Security realicen su magia.
        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() throws Exception{

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build());
        manager.createUser(User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN","USER")
                .build());

        return manager;
    }


    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}