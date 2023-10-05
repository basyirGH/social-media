package com.basyir.projects.socialmedia.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "post")

public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String content;
    private int likesCount;
    private int repliesCount;
    private LocalDateTime dateAndTimePosted;

    public Post (){

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getRepliesCount() {
        return repliesCount;
    }

    public void setRepliesCount(int repliesCount) {
        this.repliesCount = repliesCount;
    }

    public LocalDateTime getDateAndTimePosted() {
        return dateAndTimePosted;
    }

    public void setDateAndTimePosted(LocalDateTime dateAndTimePosted) {
        this.dateAndTimePosted = dateAndTimePosted;
    }
    

    

}
