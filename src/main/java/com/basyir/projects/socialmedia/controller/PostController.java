package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;
import com.basyir.projects.socialmedia.util.apiresponse.BasicMessage;
import com.basyir.projects.socialmedia.util.apiresponse.BasicResponse;
import com.basyir.projects.socialmedia.util.apiresponse.BasicStatus;
import com.basyir.projects.socialmedia.util.apiresponse.DataResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  @Autowired
  PostRelationshipRepository prRepository;

  // Note
  // @RequestParam -> /posts/create?id={id}
  // @PathVariable -> /posts/create/{id}
  @PostMapping("/create")
  public ResponseEntity<Object> createPost(@ModelAttribute("post") Post post, @RequestParam long userId) {
    try {

      Optional<User> userData = userRepository.findById(userId);
      LocalDateTime currentDateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      currentDateTime.format(formatter);

      if (userData.isPresent()) {

        User user = userData.get();
        post.setUser(user);
        post.setDateAndTimePosted(currentDateTime);
        postRepository.save(post);
        return new ResponseEntity<>(new BasicResponse(BasicStatus.SUCCESS, BasicMessage.POST_CREATED), HttpStatus.OK);

      } else {

        return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, BasicMessage.USER_NOT_FOUND),
            HttpStatus.OK);

      }

    } catch (Exception e) {

      return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @GetMapping("/get/all/from")
  public ResponseEntity<Object> getUserPosts(@RequestParam long userId) {

    Optional<User> userData = userRepository.findById(userId);

    try {
      if (userData.isPresent()) {

        // Attach user data to each post belonging to this User.
        List<Post> posts = postRepository.findByUserId(userId);
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

  @GetMapping("/get/all")
  public ResponseEntity<Object> getAllPosts() {
    try {

      List<Post> posts = postRepository.findAll();
      return new ResponseEntity<>(new DataResponse<>(BasicStatus.SUCCESS, BasicMessage.POSTS_FOUND, posts),
          HttpStatus.OK);

    } catch (Exception ex) {

      return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, ex.toString()),
          HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @GetMapping("/get")
  public ResponseEntity<Object> getPost(@RequestParam long postId) {
    try {

      Optional<Post> postData = postRepository.findById(postId);

      if (postData.isPresent()) {

        Optional<Post> post = postRepository.findById(postId);
        return new ResponseEntity<>(new DataResponse<>(BasicStatus.SUCCESS, BasicMessage.POST_FOUND, post),
            HttpStatus.OK);

      } else {

        return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, BasicMessage.POST_NOT_FOUND),
            HttpStatus.OK);

      }
    } catch (Exception ex) {

      return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, ex.toString()),
          HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @PostMapping("/reply")
  public ResponseEntity<Object> createReply(@RequestParam long parentPostId, @RequestParam long userId,
      @RequestBody Post reply) {
    try {

      Optional<User> userData = userRepository.findById(userId);
      Optional<Post> parentPostData = postRepository.findById(parentPostId);

      if (userData.isPresent() && parentPostData.isPresent()) {

        User user = userData.get();
        PostRelationship pr = new PostRelationship();

        reply.setUser(user);
        pr.setParentPost(parentPostData.get());
        pr.setChildPost(reply);

        prRepository.save(pr);
        postRepository.save(reply);
        postRepository.updateRepliesCount(parentPostId, parentPostData.get().getRepliesCount() + 1);
        return new ResponseEntity<>(new BasicResponse(BasicStatus.SUCCESS, BasicMessage.POST_CREATED),
            HttpStatus.OK);

      } else {
        return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, BasicMessage.POST_NOT_FOUND),
            HttpStatus.OK);
      }

    } catch (Exception e) {

      return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, e.toString()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

}
