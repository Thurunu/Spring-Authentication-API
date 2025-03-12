package com.thurunu.springsecex.service;

import com.thurunu.springsecex.model.Users;
import com.thurunu.springsecex.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepo.save(user);
    }

    public String verify(Users user) {
        // now we do verification process in here, for that we need AuthenticationManager object and
        // Authentication
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated())
            return "Login Successful";
        else
            return "Login Credentials Incorrect";
        // basically what we do here is we tell to spring security to do its authentication way according to our way otherwise it will do every verification behind the scene.
    }
}
