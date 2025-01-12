package com.way2p.todo.controllers;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {return "Hello from authorized API request";}
}
