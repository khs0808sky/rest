package com.example.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.rest.domain.HelloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class HelloController {
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello Spring!!!";
    }

    @GetMapping("/hello-bean")
    public HelloWorld helloBean() {
        return new HelloWorld("Hello World Bean!!!");
    }

    @GetMapping("/hello-bean/path-variable/{name}/{age}")
    public HelloWorld helloBean2(@PathVariable String name,
                                @PathVariable int age) {
        return new HelloWorld(String.format("Hello World Bean, %s, %d", name, age));
    }
    
    
    

}
