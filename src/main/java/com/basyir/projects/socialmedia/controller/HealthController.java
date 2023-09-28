package com.basyir.projects.socialmedia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class HealthController {
    @GetMapping("/")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<>("Hello and Welcome to Basyir Social Media!", HttpStatus.OK);
    }
}
