package com.basyir.projects.socialmedia.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {
    @GetMapping("/")
    public String ping() {
        return "Hello & Welcome to Basyir Social Media !!!";
    }
}
