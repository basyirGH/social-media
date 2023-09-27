package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;

//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/posts")
public class PostController {

  @Autowired
  PostRepository postRepository;
  @Autowired
  UserRepository userRepository;

  @GetMapping("/")
  public String ping() {
    return "OK";
  }

  @PostMapping("/create")
  public ResponseEntity<Post> createPost(@RequestBody PostDTO postdto) {
    try {

      long authorId = postdto.getAuthorId();

      User author = userRepository.findById(authorId)
            .orElseThrow(() -> new ResourceAccessException("User not found with id " + authorId));

      Post post = new Post();
      post.setAuthor(author);
      post.setContent(postdto.getContent());

      postRepository.save(post);
          
      return new ResponseEntity<>(post, HttpStatus.CREATED);

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
