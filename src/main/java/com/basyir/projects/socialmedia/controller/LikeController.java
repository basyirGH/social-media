package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;
import com.basyir.projects.socialmedia.util.ModelAndViewProvider;
import com.basyir.projects.socialmedia.util.apiresponse.BasicResponse;
import com.basyir.projects.socialmedia.util.apiresponse.BasicStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @PostMapping("/push")
  public ResponseEntity<Object> pushLike(@RequestParam long postId, @RequestParam long userId) {
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
        postRepository.updateLikesCount(postId, post.getLikesCount() + 1);
        return new ResponseEntity<>(new BasicResponse(BasicStatus.SUCCESS, "Post liked."), HttpStatus.OK);

      } else {

        return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, "Post not found."), HttpStatus.OK);

      }

    } catch (Exception e) {
      return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/get/all/for")
  public ResponseEntity<Object> getPostLikes(@RequestParam long postId) {
    try {
      Optional<User> userData = userRepository.findById(postId);

      if (userData.isPresent()) {

        // Attach user data to each post belonging to this User.
        List<Post> posts = postRepository.findByUserId(postId);
        posts.forEach(item -> {
          item.setUser(userData.get());
        });

        return new ResponseEntity<>(posts, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, "User not found."), HttpStatus.OK);
      }
    } catch (Exception e) {

      return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

}
