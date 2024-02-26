package com.basyir.projects.socialmedia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.basyir.projects.socialmedia.model.Post;
import com.basyir.projects.socialmedia.repository.PostRepository;
import com.basyir.projects.socialmedia.util.apiresponse.BasicResponse;
import com.basyir.projects.socialmedia.util.apiresponse.BasicStatus;

@RestController
public class SystemHealthController {

    // @GetMapping("/")
    // public ResponseEntity<BasicResponse> ping() {
    //     return new ResponseEntity<>(new BasicResponse(BasicStatus.SUCCESS, "Welcome to Basyir Social Media!."), HttpStatus.OK);
    // }

    @Autowired
    PostRepository postRepository;

    @GetMapping("/")
    public ModelAndView ping() {
      
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        Long id = Long.valueOf(1);
        Optional<Post> postData = postRepository.findById(id);
        mav.addObject("post", postData.get());
        return mav;
    }
}
