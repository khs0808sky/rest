package com.example.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rest.domain.User;
import com.example.rest.mapper.UserMapper;

@Service  // @Controller, @Service, @Repository, @Component
public class UserService {
    @Autowired
    private UserMapper mapper;

    // private static List<User> users = new ArrayList<>();
    // private static int usersCount = 3;

    // static{
    //     users.add(new User(1, "kim", new Date()));
    //     users.add(new User(2, "park", new Date()));
    //     users.add(new User(3, "lee", new Date()));
    // }

    public List<User> findAll(){
        return mapper.findAllUsers();
    }

    public User findOne(int id) {
        return mapper.findUser(id);
    }

    public User save(User user){
        mapper.createUser(user);

        return user;
    }

    public User deleteById(int id){
        mapper.deleteUser(id);
        return null;
    }
}
