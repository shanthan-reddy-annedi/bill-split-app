package com.example.billSplit.controllers;

import com.example.billSplit.dtos.AuthRequest;
import com.example.billSplit.dtos.AuthResponse;
import com.example.billSplit.entites.User;
import com.example.billSplit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("users/{userID}")
    public User getUserById(@PathVariable Long userID) {
        return userService.getUserById(userID);
    }

    @PostMapping("auth/users/register")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("users/{userID}")
    public User updateUser(@PathVariable Long userID, @RequestBody User user) {
        return userService.updateUser(userID, user);
    }

    @PostMapping("auth/users/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }

    @DeleteMapping("users/{userID}")
    public void deleteUser(@PathVariable Long userID) {
        userService.deleteUser(userID);
    }
}
