package com.example.tan.finalproject.models;

import java.util.ArrayList;

/**
 * Created by Tan on 3/23/2017.
 */

public class Group {
    String groupID;
    String groupName;
    ArrayList<User> userList;

    public Group() {

    }

    public Group(String groupID, String groupName, ArrayList<User> userList) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.userList = userList;
    }

    public Group(String groupName, ArrayList<User> userList) {
        this.groupName = groupName;
        this.userList = userList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
