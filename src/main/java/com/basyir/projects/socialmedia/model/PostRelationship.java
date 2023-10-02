package com.basyir.projects.socialmedia.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PostRelationship {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_post_id")
    private Post parentPost;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "child_post_id")
    private Post childPost;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getParentPost() {
        return parentPost;
    }

    public void setParentPost(Post parentPost) {
        this.parentPost = parentPost;
    }

    public Post getChildPost() {
        return childPost;
    }

    public void setChildPost(Post childPost) {
        this.childPost = childPost;
    }

    
}
