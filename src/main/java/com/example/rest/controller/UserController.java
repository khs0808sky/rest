package com.example.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.domain.User;
import com.example.rest.exception.UserNotFoundException;
import com.example.rest.service.UserService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class UserController {
   
   private UserService service;
   
   public UserController(UserService service){
    this.service = service;
   }
  
   @GetMapping("/users")
   public List<User> retrieveAllUsers() {
       return service.findAll();
   }  
  
   @GetMapping("/users/{id}")
   public User retriveUser(@PathVariable int id) {
       User user = service.findOne(id); 

       if(user == null){
        throw new UserNotFoundException(String.format("ID[%s] not found", id));
       }

       return user;
   }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
       User savedUser = service.save(user);
       URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
       
       return ResponseEntity.created(location).build();
   }
   
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null){
        throw new UserNotFoundException(String.format("ID[%s] not found", id));
       }
   }
}
