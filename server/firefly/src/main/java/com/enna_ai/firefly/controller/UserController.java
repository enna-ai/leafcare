package com.enna_ai.firefly.controller;

import com.enna_ai.firefly.model.User;
import com.enna_ai.firefly.repository.UserRepository;
import com.enna_ai.firefly.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = (User) userDetails;

        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        UserDetails userDetails = userDetailsService.loadUserById(id);
        User user = (User) userDetails;

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable UUID id) {
        userDetailsService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/")
    public ResponseEntity<User> deleteAllUsers() {
        userDetailsService.deleteAllUsers();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable UUID id, @RequestBody User updatedUser) {
        User editedUser = userDetailsService.updateUserById(id, updatedUser);
        return ResponseEntity.ok(editedUser);
    }
}
