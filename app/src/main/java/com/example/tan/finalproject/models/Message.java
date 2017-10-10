package com.example.tan.finalproject.models;

import java.util.Date;

/**
 * Created by Tan on 3/24/2017.
 */

public class Message {
    String sender;
    String receiver;
    String msg;
    long time;
    boolean left;

    public Message(String sender, String receiver, String msg, long time,boolean left) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.time = time;
        this.left = left;
    }

    public Message(String sender, String receiver, String msg,long time) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.time = time;
    }

    public Message() {

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}

