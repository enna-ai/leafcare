package com.enna_ai.firefly.service;

import com.enna_ai.firefly.model.User;
import com.enna_ai.firefly.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }

    public UserDetails loadUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User email not found."));
    }

    public UserDetails loadUserById(UUID id) {
        return repository.findUserById(id)
                .orElseThrow(() -> new NoSuchElementException("User Id not found."));
    }

    public void deleteUserById(UUID id) {
        loadUserById(id);
        repository.deleteById(id);
    }

    public void deleteAllUsers() {
        repository.deleteAll();
    }

    public User updateUserById(UUID id, User updatedUser) {
        return repository.findById(id)
                .map(user -> {
                    Optional.ofNullable(updatedUser.getUsername()).ifPresent(user::setUsername);
                    Optional.ofNullable(updatedUser.getPassword()).ifPresent(user::setPassword);

                    return repository.save(user);
                })
                .orElseThrow(() -> new NoSuchElementException("User ID not found."));
    }
}
