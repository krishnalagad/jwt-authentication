package com.jwt.authentication.service;

import com.jwt.authentication.entity.User;
import com.jwt.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return this.userRepository.findAll();
    }

    public User createUser(User user) {

        user.setUserId(UUID.randomUUID().toString());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }


//    private List<User> users = new ArrayList<>();

//    public UserService() {
//        this.users.add(new User(UUID.randomUUID().toString(), "Krishna Lagad", "krishnalagad@gmail.com"));
//        this.users.add(new User(UUID.randomUUID().toString(), "Varsha Lagad", "varsha@gmail.com"));
//        this.users.add(new User(UUID.randomUUID().toString(), "Geeta Lagad", "Geeta@gmail.com"));
//        this.users.add(new User(UUID.randomUUID().toString(), "Aakanksha Lagad", "Aakanksha@gmail.com"));
//        this.users.add(new User(UUID.randomUUID().toString(), "Dilip Lagad", "Dilip@gmail.com"));
//    }

//    public List<?> getUsers() {
//        return this.users;
//    }
}
