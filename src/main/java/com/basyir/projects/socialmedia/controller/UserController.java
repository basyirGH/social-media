package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;
import com.basyir.projects.socialmedia.util.ModelAndViewProvider;
import com.basyir.projects.socialmedia.util.apiresponse.BasicMessage;
import com.basyir.projects.socialmedia.util.apiresponse.BasicResponse;
import com.basyir.projects.socialmedia.util.apiresponse.BasicStatus;
import com.basyir.projects.socialmedia.util.apiresponse.DataResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  PostRepository postRepository;
  @Autowired
  UserRepository userRepository;

    // GET ALL USER POSTS
    @GetMapping("/{userId}/posts")
    public ResponseEntity<Object> getUserPosts(@PathVariable long userId) {
  
      Optional<User> userData = userRepository.findById(userId);
  
      try {
        if (userData.isPresent()) {
  
          // Attach user data to each post belonging to this User.
          List<Post> posts = postRepository.findPostsByUserId(userId);
          posts.forEach(item -> {
            item.setUser(userData.get());
          });
  
          return new ResponseEntity<>(new DataResponse<>(BasicStatus.SUCCESS, BasicMessage.POSTS_FOUND, posts),
              HttpStatus.OK);
  
        } else {
  
          return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, BasicMessage.USER_NOT_FOUND),
              HttpStatus.OK);
  
        }
      } catch (Exception ex) {
  
        return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, ex.toString()),
            HttpStatus.INTERNAL_SERVER_ERROR);
  
      }
    }
}
