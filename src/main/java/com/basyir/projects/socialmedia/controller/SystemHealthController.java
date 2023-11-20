package com.basyir.projects.socialmedia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.basyir.projects.socialmedia.util.apiresponse.BasicResponse;
import com.basyir.projects.socialmedia.util.apiresponse.BasicStatus;

@RestController
public class SystemHealthController {

    @GetMapping("/")
    public ResponseEntity<BasicResponse> ping() {
        return new ResponseEntity<>(new BasicResponse(BasicStatus.SUCCESS, "Welcome to Basyir Social Media!."), HttpStatus.OK);
    }
}
