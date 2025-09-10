package com.example.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rest.domain.User;

@Service // @Controller, @Service, @Repository, @Component
public class UserService {
    private static List<User> users = new ArrayList<>();
    private static int usersCount = 3;

    static {
        users.add(new User(1, "kim", new Date()));
        users.add(new User(2, "park", new Date()));
        users.add(new User(3, "lee", new Date()));
    }

    public List<User> findAll() {
        return users;
    }

    public User findOne(int id) {
        for(User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++usersCount);
        }

        if (user.getJoinDate() == null) {
            user.setJoinDate(new Date());
        }

        users.add(user);

        return user;
    }
}
