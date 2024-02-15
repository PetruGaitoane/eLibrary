package com.example.demo.helpers;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CustomErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeresponse;
    private String status;
    private String message;

    public CustomErrorResponse() {
        this.timeresponse = LocalDateTime.now();
    }

    public CustomErrorResponse(String status, String message) {
        this.timeresponse = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimeresponse() {
        return timeresponse;
    }

    public void setTimeresponse(LocalDateTime timeresponse) {
        this.timeresponse = timeresponse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
