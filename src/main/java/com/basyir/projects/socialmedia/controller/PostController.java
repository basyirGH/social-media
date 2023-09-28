package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;

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
  // @PathVariable -> /posts/create/[id]
  @PostMapping("/create")
  public ResponseEntity<String> createPost(@PathVariable long authorId, @RequestBody Post post) {
    try {

      Optional<User> authorData = userRepository.findById(authorId);

      if (authorData.isPresent()) {
        User author = authorData.get();
        post.setAuthor(author);
        post.setContent(post.getContent());
        postRepository.save(post);
        return new ResponseEntity<>("Post Created", HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>("Author not found.", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
