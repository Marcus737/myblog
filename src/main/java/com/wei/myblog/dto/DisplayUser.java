package com.wei.myblog.dto;

import com.wei.myblog.entity.User;

import java.sql.Timestamp;

/**
 * 返回给客户端的User
 */
public class DisplayUser {

    private Integer userId;
    private String username;
    private Integer state;
    private Timestamp loginTime;
    private String avatar;
    private String email;

    public DisplayUser(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.state = user.getState();
        this.loginTime = user.getLoginTime();
        this.avatar = user.getAvatar();
        this.email = user.getEmail();
    }


    public DisplayUser() {
    }

    public DisplayUser(Integer userId, String username, Integer state, Timestamp loginTime, String avatar, String email) {
        this.userId = userId;
        this.username = username;
        this.state = state;
        this.loginTime = loginTime;
        this.avatar = avatar;
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "DisplayUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", state=" + state +
                ", loginTime=" + loginTime +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
