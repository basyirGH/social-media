package com.basyir.projects.socialmedia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HealthController {
    @GetMapping("/")
    public ModelAndView ping() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }
}
