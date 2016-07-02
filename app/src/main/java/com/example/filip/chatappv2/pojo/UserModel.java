package com.example.filip.chatappv2.pojo;

import com.example.filip.chatappv2.utils.StringUtils;

/**
 * Created by flip6 on 10.6.2016..
 */
public class UserModel extends BaseModel{
    private String userDisplayName;
    private boolean isOnline;

    public UserModel(String userDisplayName, boolean isOnline) {
        this.userDisplayName = userDisplayName;
        this.isOnline = isOnline;
    }

    public UserModel() {
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    @Override
    protected boolean validate() {
        return !StringUtils.stringsAreNullOrEmpty(userDisplayName);
    }
}