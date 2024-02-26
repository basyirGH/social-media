package com.basyir.projects.socialmedia.controller;

import com.basyir.projects.socialmedia.model.*;
import com.basyir.projects.socialmedia.repository.*;
import com.basyir.projects.socialmedia.dto.*;
import com.basyir.projects.socialmedia.util.apiresponse.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/posts")
public class PostController {

  @Autowired
  PostRepository postRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  PostRelationshipRepository prRepository;
  @Autowired
  LikeRepository likeRepository;

  // Note
  // @RequestParam -> /posts?id=123
  // @PathVariable -> /posts/123
  // CREATE A POST
  @PostMapping("/")
  public ResponseEntity<Object> createPost(@RequestBody CreatePostDTO requestBody) {
    try {

      Optional<User> userData = userRepository.findById(requestBody.getUserId());
      LocalDateTime currentDateTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      currentDateTime.format(formatter);

      if (userData.isPresent()) {

        User user = userData.get();
        Post post = new Post();
        post.setUser(user);
        post.setContent(requestBody.getContent());
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

  // GET A POST BY USER ID 
  @GetMapping("/{postId}")
  public ResponseEntity<Object> getPost(@PathVariable long postId) {
    try {

      Optional<Post> postData = postRepository.findById(postId);

      if (postData.isPresent()) {

        Post post = postData.get();
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


  // GET ALL POSTS IN THE SYSTEM
  @GetMapping("/all")
  public ModelAndView getAllPosts() {
    try {

      List<Post> posts = postRepository.findAll();
      ModelAndView mav = new ModelAndView();
      mav.addObject("posts", posts);
      mav.setViewName("index");
      Long id = Long.valueOf(1);
      Optional<Post> postData = postRepository.findById(id);
      mav.addObject("post", postData.get());
      return mav;
      // return new ResponseEntity<>(new DataResponse<>(BasicStatus.SUCCESS, BasicMessage.POSTS_FOUND, posts),
      //     HttpStatus.OK);

    } catch (Exception ex) {
      ModelAndView mav = new ModelAndView();
      mav.setViewName("error");
      mav.addObject("errorMessage", ex.toString());
      return mav;
      // return new ResponseEntity<>(new BasicResponse(BasicStatus.ERROR, ex.toString()),
      //     HttpStatus.INTERNAL_SERVER_ERROR);

    }
  }

  @PostMapping("/{postId}/like/{userId}")
  public ResponseEntity<Object> likePost(@PathVariable long postId, @PathVariable long userId) {
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
