package com.java.userservice.controller;

import com.java.userservice.entity.User;
import com.java.userservice.model.ResponseTemplate;
import com.java.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public User saveUser(@RequestBody User user) {
        log.info("Inside saveUser method of User Controller ..");
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public ResponseTemplate getUserWithDepartment(@PathVariable("id") Long userId){
        log.info("Inside getUserWithDepartment method of User Controller ..");
        return userService.getUserWithDepartment(userId);
    }
}
