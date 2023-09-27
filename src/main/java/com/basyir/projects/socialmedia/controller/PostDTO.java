package com.basyir.projects.socialmedia.controller;

public class PostDTO {

    private String content;
    private Long authorId;

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    } 
    
    
}
