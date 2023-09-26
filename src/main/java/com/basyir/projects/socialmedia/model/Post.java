package com.basyir.projects.socialmedia.model;

import java.util.ArrayList;
import jakarta.persistence.*;


@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String author;
    private String content;
    private ArrayList<Post> replies;
    private ArrayList<Like> likes;

    public Post() {

    }

    public Post(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Post> getReplies() {
        return replies;
    }

    public void setReplies(ArrayList<Post> replies) {
        this.replies = replies;
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Like> likes) {
        this.likes = likes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;
}
