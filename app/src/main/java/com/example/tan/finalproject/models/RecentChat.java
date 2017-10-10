package com.example.tan.finalproject.models;

/**
 * Created by Tan on 3/23/2017.
 */

public class RecentChat {
    String friendID;
    String friendName;
    String lastedMsg;
    long time;
    boolean read;
    public RecentChat() {
    }

    public RecentChat(String friendID,String friendName, String lastedMsg,long time,boolean read) {
        this.friendName = friendName;
        this.lastedMsg = lastedMsg;
        this.friendID = friendID;
        this.time = time;
        this.read = read;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getLastedMsg() {
        return lastedMsg;
    }

    public void setLastedMsg(String lastedMsg) {
        this.lastedMsg = lastedMsg;
    }

    public String getFriendID() {
        return friendID;
    }

    public void setFriendID(String friendID) {
        this.friendID = friendID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
