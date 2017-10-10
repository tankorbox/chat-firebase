package com.example.tan.buzzme.models;

/**
 * Created by Tan on 3/24/2017.
 */

public class Message {
    int id;
    String message;
    String senderName;

    public Message(int id, String message, String senderName) {
        this.id = id;
        this.message = message;
        this.senderName = senderName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}

