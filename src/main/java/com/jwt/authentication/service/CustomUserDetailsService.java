package com.jwt.authentication.service;

import com.jwt.authentication.entity.User;
import com.jwt.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

//    This is custom user details service. We also have made user service with in memory user in AppConfig.java.

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Load user from database.
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found !!"));

        return user;
    }
}
