package com.jwt.authentication.service;

import com.jwt.authentication.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    public UserService() {
        this.users.add(new User(UUID.randomUUID().toString(), "Krishna Lagad", "krishnalagad@gmail.com"));
        this.users.add(new User(UUID.randomUUID().toString(), "Varsha Lagad", "varsha@gmail.com"));
        this.users.add(new User(UUID.randomUUID().toString(), "Geeta Lagad", "Geeta@gmail.com"));
        this.users.add(new User(UUID.randomUUID().toString(), "Aakanksha Lagad", "Aakanksha@gmail.com"));
        this.users.add(new User(UUID.randomUUID().toString(), "Dilip Lagad", "Dilip@gmail.com"));
    }

    public List<?> getUsers() {
        return this.users;
    }
}
