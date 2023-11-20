package com.basyir.projects.socialmedia.util.apiresponse;

public class DataResponse<T> extends BasicResponse {

    private T data;

    public DataResponse(BasicStatus status, Object message, T data) {
        super(status, message);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    
    
}
