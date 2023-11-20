package com.basyir.projects.socialmedia.util.apiresponse;

public class BasicResponse {
    
    private BasicStatus status;
    private Object message;

    public BasicResponse(BasicStatus status, Object message) {
        this.status = status;
        this.message = message;
    }

    public BasicStatus getStatus() {
        return status;
    }
    public void setStatus(BasicStatus status) {
        this.status = status;
    }
    public Object getMessage() {
        return message;
    }
    public void setMessage(Object message) {
        this.message = message;
    }

    
}
