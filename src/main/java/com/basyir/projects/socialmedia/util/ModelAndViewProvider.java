package com.basyir.projects.socialmedia.util;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.basyir.projects.socialmedia.model.Post;
import com.basyir.projects.socialmedia.repository.PostRepository;

@Component
public class ModelAndViewProvider {

    public ModelAndView getIndexTemplate(PostRepository postRepository){

        List<Post> posts = postRepository.findPostsOrderByDateAndTimePosted();
        ModelAndView mav = new ModelAndView("/index");

        mav.addObject("post", new Post());
        mav.addObject("posts", posts);
        return mav;

    }

    public ModelAndView getError(String errorMessage){

        ModelAndView mav = new ModelAndView("/error");
        mav.addObject("errorMessage", errorMessage);
        return mav;

    }

}