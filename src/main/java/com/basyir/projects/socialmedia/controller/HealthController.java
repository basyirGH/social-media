package com.basyir.projects.socialmedia.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.basyir.projects.socialmedia.model.Post;

@RestController
public class HealthController {
    @GetMapping("/")
    public ModelAndView ping() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("post", new Post());
        mav.setViewName("index");
        return mav;
    }
}
