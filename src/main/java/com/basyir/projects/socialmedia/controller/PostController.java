package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
  public RedirectView createPost(@ModelAttribute("post") Post post, @RequestParam long userId,
      BindingResult bindingResult) {
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
        return new RedirectView("/");

      } else {

        RedirectView rd = new RedirectView("/error");
        rd.addStaticAttribute("msg", "User not found");
        return rd;

      }

    } catch (Exception e) {

      RedirectView rd = new RedirectView("/error");
      rd.addStaticAttribute("msg", e);
      return rd;

    }
  }

  @GetMapping("/get/all/from")
  public ModelAndView getUserPosts(@RequestParam long userId) {

    Optional<User> userData = userRepository.findById(userId);
    ModelAndView rd;

    try {
      if (userData.isPresent()) {

        // Attach user data to each post belonging to this User.
        List<Post> posts = postRepository.findByUserId(userId);
        posts.forEach(item -> {
          item.setUser(userData.get());
        });
        rd = new ModelAndView("/index");
        rd.addObject("post", new Post());
        // posts.sort(Collections.reverseOrder());
        rd.addObject("posts", posts);
        return rd;

      } else {

        rd = new ModelAndView("/error");
        rd.addObject("msg", "User not found");
        return rd;

      }
    } catch (Exception ex) {

      rd = new ModelAndView("/error");
      rd.addObject("msg", ex.toString());
      System.out.println(ex.toString());
      return rd;

    }
  }

  @GetMapping("/get/all")
  public ModelAndView getAllPosts() {

    // List<User> userData = userRepository.findAll();
    ModelAndView rd;

    try {

      // Attach user data to each post belonging to this User.
      List<Post> posts = postRepository.findAll();
      rd = new ModelAndView("/index");
      rd.addObject("post", new Post());
      // posts.sort(Collections.reverseOrder());
      rd.addObject("posts", posts);
      return rd;

    } catch (Exception ex) {

      rd = new ModelAndView("/error");
      rd.addObject("msg", ex.toString());
      System.out.println(ex.toString());
      return rd;

    }
  }

  @GetMapping("/get")
  public ResponseEntity<Object> getPost(@RequestParam long postId) {
    try {

      Optional<Post> postData = postRepository.findById(postId);

      if (postData.isPresent()) {

        Optional<Post> post = postRepository.findById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);

      } else {

        return new ResponseEntity<>("Post not found.", HttpStatus.NOT_FOUND);

      }
    } catch (Exception ex) {

      return new ResponseEntity<>(ex.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @PostMapping("/reply")
  public ResponseEntity<String> createReply(@RequestParam long parentPostId, @RequestParam long userId,
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
        return new ResponseEntity<>("Post Created", HttpStatus.CREATED);

      } else {
        return new ResponseEntity<>("Author/Post not found.", HttpStatus.NOT_FOUND);
      }

    } catch (Exception e) {
      return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
