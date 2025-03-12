package com.thurunu.springsecex;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(customizer -> customizer.disable()); // this will disable csrf tokens
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated()); // by adding this no one can access the page without login credentials
        http.formLogin(Customizer.withDefaults()); //this will add the login form
        http.httpBasic(Customizer.withDefaults()); //this line is for use postman to handle requests
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));




        return  http.build();
    }
}