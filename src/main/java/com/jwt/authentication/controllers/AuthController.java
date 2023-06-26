package com.jwt.authentication.controllers;

import com.jwt.authentication.entity.RefreshToken;
import com.jwt.authentication.entity.User;
import com.jwt.authentication.models.JwtRequest;
import com.jwt.authentication.models.JwtResponse;
import com.jwt.authentication.models.RefreshTokenRequest;
import com.jwt.authentication.security.JwtHelper;
import com.jwt.authentication.service.RefreshTokenService;
import com.jwt.authentication.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(userDetails.getUsername());

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .refreshToken(refreshToken.getRefreshToken())
                .username(userDetails.getUsername()).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            this.manager.authenticate(authentication);
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Invalid username or password !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(BadCredentialsException e) {
        return "Invalid username or password !!";
    }

    @PostMapping("/create-user")
    public User createUser(@RequestBody User user) {
        return this.userService.createUser(user);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken = this.refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();
        String token = this.helper.generateToken(user);

        return ResponseEntity.ok(JwtResponse.builder()
                .refreshToken(refreshToken.getRefreshToken())
                .jwtToken(token)
                .username(user.getEmail())
                .build()
        );
    }
}
