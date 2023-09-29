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
@RequestMapping("/likes")
public class LikeController {

  @Autowired
  PostRepository postRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  LikeRepository likeRepository;

  @PostMapping("/push/{postId}/{userId}")
  public ResponseEntity<String> pushLike(@PathVariable long postId, @PathVariable long userId) {
    try {

      Optional<Post> postData = postRepository.findById(postId);
      Optional<User> userData = userRepository.findById(userId);

      if (postData.isPresent() && userData.isPresent()) {
        Post post = postData.get();
        User user = userData.get();
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        likeRepository.save(like);
        return new ResponseEntity<>("Post Liked", HttpStatus.CREATED);
      } else {
        return new ResponseEntity<>("Post/User not found.", HttpStatus.NOT_FOUND);
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
