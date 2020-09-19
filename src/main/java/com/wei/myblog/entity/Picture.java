package com.wei.myblog.entity;

import java.sql.Timestamp;

public class Picture {
    private Integer pictureId;
    private Timestamp pictureTime;
    private String pictureUrl;

    public Picture() {
    }

    public Picture(Integer pictureId, Timestamp pictureTime, String pictureUrl) {
        this.pictureId = pictureId;
        this.pictureTime = pictureTime;
        this.pictureUrl = pictureUrl;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public Timestamp getPictureTime() {
        return pictureTime;
    }

    public void setPictureTime(Timestamp pictureTime) {
        this.pictureTime = pictureTime;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "pictureId=" + pictureId +
                ", pictureTime=" + pictureTime +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
