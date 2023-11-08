package com.sandrajavaschool.OnlineStore.security.config;

import com.sandrajavaschool.OnlineStore.authHandler.LoginSuccessHandler;

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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration //le indica al contenedor de spring que esta es una clase de seguridad en el momento de iniciar la app
@EnableWebSecurity //Se activa la seguridad web en nuestra app, y esta clase contiene toda la config referente a la seguridad
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true) // , (opcional)
@RequiredArgsConstructor
public class SecurityConfig {

    /*
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    //Se encarga de verificar la informacion de los usuarios que le loggean en nuestra app

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    //Con este metodo encriptamos todas nuestras contraseñas
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Se encarga de incorporar el filtro de seguridad de jwt que creamos anteriormente

    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    //establece una cadena de filtros de seguridad en nuestra app, es aqui donde
    //determinaremos los permisos segun los roles de usuarios para acceder a nuestra app

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling().accessDeniedHandler("error")
                .authenticationEntryPoint(authenticationEntryPoint); //nos establece un punto de entrada personalizada de autenticacion para el maenjo de autenticaciones no autorizadas

        http
                .sessionManagement(SessionCreationPolicy.STATELESS); //permite la gestion de sesiones sin estado

        http
                .authorizeHttpRequests((authz) -> {
                    try {
                        //aqui van las estaticas, acceso a todos los recursos estaticos
                        //y permitimos el acceso a todos
                        authz.requestMatchers("/css/**", "/js/**", "/images/**", "/product/productsList", "/api/auth/**").permitAll()
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

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)


        return http.build();    }

*/


    //Configuramos el Metodo HTTPSecurity para indicar la cadena de filtros
    // de Autotización que vamos a seguir;

    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*
    @Autowired
    public void userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

*/


    ///se puede dar seguridad desde los controladores en lugar de requestMatcher
    //1.- Usamos anotaciones en los metodos handler del controlador Secured(""), o en la clase completa del controlador
    //2.- Habilitamos estas anotaciones incluyendo @EnableMethodSecurity en el SecurityConfig
    //3.- al habilitar el metodo prePostEnabled, podemos usar en el metodo handler del controlador @PreAutorize("hasRole()") //hasAnyRole


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
                .successHandler(loginSuccessHandler)
                .permitAll());

        // Configuramos el sistema de cierre de sesión de la aplicación como el cierre de sesión predeterminado de Spring Security.

        http.logout((logout) -> logout.permitAll()
                .logoutSuccessUrl("/"));

        //PENDIENTE: lanzar un mensaje de excepcion para el error 403 en spring security 6
        //http.exceptionHandling("/error/error403");
        http.exceptionHandling().accessDeniedPage("/error/error403");
        // Devolvemos el objeto HttpSecurity configurado para que Spring Boot y Spring Security realicen su magia.

        //http.csrf(AbstractHttpConfigurer::disable);

        //Desabilitamos el uso de sesion
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


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




}