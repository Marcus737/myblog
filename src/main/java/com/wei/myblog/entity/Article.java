package com.wei.myblog.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * 文章类
 */
public class Article implements Serializable {
    /**
     * 文章id
     */
    private String articleId;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 简短描述
     */
    private String briefDescription;
    /**
     * 作者
     */
    private String author;
    /**
     * 点击数
     */
    private Integer clicks;
    /**
     * 本地markdown的路径
     */
    private String markdownUrl;
    /**
     * 封面地址
     */
    private String cover;
    /**
     * 更新时间
     */
    private Timestamp updateTime;
    /**
     * 评论
     */
    private List<Comment> comments;
    /**
     * 所属标签
     */
    private List<Label> labels;

    public Article() {
    }

    public Article(String articleId, String title, String briefDescription, String author, Integer clicks, String markdownUrl, String cover, Timestamp updateTime, List<Comment> comments, List<Label> labels) {
        this.articleId = articleId;
        this.title = title;
        this.briefDescription = briefDescription;
        this.author = author;
        this.clicks = clicks;
        this.markdownUrl = markdownUrl;
        this.cover = cover;
        this.updateTime = updateTime;
        this.comments = comments;
        this.labels = labels;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getClicks() {
        return this.clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public String getMarkdownUrl() {
        return markdownUrl;
    }

    public void setMarkdownUrl(String markdownUrl) {
        this.markdownUrl = markdownUrl;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", title='" + title + '\'' +
                ", briefDescription='" + briefDescription + '\'' +
                ", author='" + author + '\'' +
                ", Clicks=" + clicks +
                ", markdownUrl='" + markdownUrl + '\'' +
                ", cover='" + cover + '\'' +
                ", updateTime=" + updateTime +
                ", comments=" + comments +
                ", labels=" + labels +
                '}';
    }
}
