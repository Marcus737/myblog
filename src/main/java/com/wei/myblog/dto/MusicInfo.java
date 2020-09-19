package com.wei.myblog.dto;

import java.util.Arrays;
import java.util.List;

public class MusicInfo {

    private String title;
    private String artist;
    private String src;
    private String pic;
    private String lrc;
    private String musicRId;
    private String musicId;

    public MusicInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getMusicRId() {
        return musicRId;
    }

    public void setMusicRId(String musicRId) {
        this.musicRId = musicRId;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    @Override
    public String toString() {
        return "MusicInfo{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", src='" + src + '\'' +
                ", pic='" + pic + '\'' +
                ", lrc='" + lrc + '\'' +
                ", musicRId='" + musicRId + '\'' +
                ", musicId='" + musicId + '\'' +
                '}';
    }
}
