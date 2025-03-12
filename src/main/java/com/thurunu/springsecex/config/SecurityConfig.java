package com.thurunu.springsecex.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(customizer -> customizer.disable()) // this will disable csrf tokens
            .authorizeHttpRequests(request -> request
                    .requestMatchers("register", "login") // we tell to spring security to ignore allowing request without
                    .permitAll() // this will give allowence
                   .anyRequest().authenticated()) // by adding this any one cann access the page witch urls ends with this endpoint without requesting authentication
//            .formLogin(Customizer.withDefaults()) //this will add the login form
                //by removing forLogin() we remove login from which appear in the browser and we only enable login request through api requests
            .httpBasic(Customizer.withDefaults()) //this line is for use postman to handle requests
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // this is for study purpose and by doing this we don't use any password encoder so password will save to the database as it is
        // we cannot use above NoPassowrdEncoder anymore cause when we use password encryption so to validate encryption password we do as follow,
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12)); // for this application we use bcrypt encoder so we make a new object and pass the strength we used for encrypt password and that's all
        provider.setUserDetailsService(userDetailsService); //user detail service is used o verify user details and here we are define a custom one
        return provider;
    }

    // add authentication manager layer for JWT tokens enabling
    @Bean
    // now we have to pass object of authenticationmanager object like we do in Authentication provider
    // but the issue is AuthenticationManager is a interface so we have to find a class where its implemented or other way and we choose to pass AuthenticationConfig as a parameter to the method
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

// this is how we define user to the system, and how spring security allow users
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("Tharuka")
//                .password("12345678")
//                .build();
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("Chamodi")
//                .password("c1234")
//                .build();
//
//// this will tell to spring security to these are particular user credentials if its correct log in them
//        return new InMemoryUserDetailsManager(user1, user2);
//        // but this cannot use in developing this is only for study
//    }


}