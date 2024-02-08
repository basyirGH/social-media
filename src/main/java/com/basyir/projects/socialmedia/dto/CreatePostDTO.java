package com.basyir.projects.socialmedia.dto;

public class CreatePostDTO {
    
    private long userId;
    private String content;
    
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    

}
