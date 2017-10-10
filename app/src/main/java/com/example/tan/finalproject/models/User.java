package com.example.tan.finalproject.models;

import java.io.Serializable;

/**
 * Created by Tan on 3/23/2017.
 */

public class User implements Serializable{
    String name;
    String userId;
    String avatar;
    String email;
    boolean isOnline;
    boolean isPick;

    User(){

    }

    public User(String name, String userId, String avatar, String email, boolean isOnline) {
        this.name = name;
        this.userId = userId;
        this.avatar = avatar;
        this.email = email;
        this.isOnline = isOnline;
    }

    public User(String name, String userId, String avatar, String email, boolean isOnline, boolean isPick) {
        this.name = name;
        this.userId = userId;
        this.avatar = avatar;
        this.email = email;
        this.isOnline = isOnline;
        this.isPick = isPick;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isPick() {
        return isPick;
    }

    public void setPick(boolean pick) {
        isPick = pick;
    }
}
