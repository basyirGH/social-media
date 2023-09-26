package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PostController {

  @Autowired
  PostRepository postRepository;

  @PostMapping("/posts")
  public ResponseEntity<Post> createPost(@RequestBody Post post) {
    try {

      Post _post = postRepository.save(
          new Post(post.getAuthor(), post.getContent()));
          
      return new ResponseEntity<>(_post, HttpStatus.CREATED);

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
