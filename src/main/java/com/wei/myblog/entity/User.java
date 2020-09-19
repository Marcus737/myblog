package com.wei.myblog.entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class User implements Serializable {
    private Integer userId;
    private String username;
    private String password;
    private Integer state;
    private Timestamp registerTime;
    private Timestamp loginTime;
    private String avatar;
    private String email;
    private List<Role> roles;
    private List<Article> articles;
    private List<Comment> comments;
    private List<Picture> pictures;

    public User() {
    }

    public User(Integer userId, String username, String password, Integer state, Timestamp registerTime, Timestamp loginTime, String avatar, String email, List<Role> roles, List<Article> articles, List<Comment> comments, List<Picture> pictures) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.state = state;
        this.registerTime = registerTime;
        this.loginTime = loginTime;
        this.avatar = avatar;
        this.email = email;
        this.roles = roles;
        this.articles = articles;
        this.comments = comments;
        this.pictures = pictures;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", state=" + state +
                ", registerTime=" + registerTime +
                ", loginTime=" + loginTime +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", articles=" + articles +
                ", comments=" + comments +
                ", pictures=" + pictures +
                '}';
    }
}
