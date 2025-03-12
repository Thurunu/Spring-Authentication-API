package com.thurunu.springsecex.controller;

import com.thurunu.springsecex.model.Users;
import com.thurunu.springsecex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        System.out.println(user);
        return userService.register(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody Users user) {

        // we need to verify user login details in here
        return userService.verify(user);
    }
}
