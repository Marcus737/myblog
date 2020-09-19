package com.wei.myblog.entity;

import com.wei.myblog.dto.DisplayUser;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 评论类
 */
public class Comment implements Serializable {
    /**
     * 评论id
     */
    private String commentId;

    /**
     * 评论时间
     */
    private Timestamp commentTime;
    /**
     * 评论内容
     */
    private String commentContent;

    private DisplayUser displayUser;



    public Comment() {
    }


    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public DisplayUser getDisplayUser() {
        return displayUser;
    }

    public void setDisplayUser(DisplayUser displayUser) {
        this.displayUser = displayUser;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", commentTime=" + commentTime +
                ", commentContent='" + commentContent + '\'' +
                ", displayUser=" + displayUser +
                '}';
    }
}
