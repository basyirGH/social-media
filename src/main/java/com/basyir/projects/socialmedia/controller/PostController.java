package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

  @Autowired
  PostRepository postRepository;
  @Autowired
  UserRepository userRepository;

  // Note
  // @RequestParam -> /posts/create?id=[id]
  // @PathVariable -> /posts/create/{id}
  @PostMapping("/create/{userId}")
  public ResponseEntity<String> createPost(@PathVariable long userId, @RequestBody Post post) {
    try {

      Optional<User> userData = userRepository.findById(userId);

      if (userData.isPresent()) {
        User user = userData.get();
        post.setUser(user);
        post.setContent(post.getContent());
        postRepository.save(post);
        return new ResponseEntity<>("Post Created", HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>("Author not found.", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/all")
  public ResponseEntity<Object> getUserPosts(@RequestParam long userId) {
    try {
      Optional<User> userData = userRepository.findById(userId);

      if (userData.isPresent()) {

        // Attach user data to each post belonging to this User.
        List<Post> posts = postRepository.findByUserId(userId);
        posts.forEach(item -> {
          item.setUser(userData.get());
        });

        return new ResponseEntity<>(posts, HttpStatus.OK);
      } else {
        return new ResponseEntity<>("Author not found.", HttpStatus.NOT_FOUND);
      }
    } catch (Exception ex) {
      return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}

//get one post
//create one reply
