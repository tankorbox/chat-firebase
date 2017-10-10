package com.example.tan.finalproject.models;

/**
 * Created by Tan on 5/9/2017.
 */

public class GroupMessage {
    String senderID;
    String senderName;
    String msg;
    long time;
    boolean left;

    public GroupMessage(String senderID, String senderName, String msg, long time, boolean left) {
        this.senderID = senderID;
        this.senderName = senderName;
        this.msg = msg;
        this.time = time;
        this.left = left;
    }


    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public GroupMessage() {
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
