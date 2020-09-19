package com.wei.myblog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * 读取项目相关配置
 */
@Component
@ConfigurationProperties(prefix = "file-info")
public class FileConfig {

    /**
     * 基础路径
     */
    private String basePath;

    /**
     * 文件大小
     */
    private Long fileMaxSize;

    /**
     * 文件名长度
     */
    private Integer filenameMaxLength;

    /**
     * 允许的后缀名
     */
    private String[] allowedSuffixNames;

    private String baseDirAvatar;

    private String baseDirMarkdown;

    private String baseDirCover;

    private String baseDirPicture;

    private String markdownExtension;

    private String defaultImgType;



    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public Long getFileMaxSize() {
        return fileMaxSize;
    }

    public void setFileMaxSize(Long fileMaxSize) {
        this.fileMaxSize = fileMaxSize;
    }

    public Integer getFilenameMaxLength() {
        return filenameMaxLength;
    }

    public void setFilenameMaxLength(Integer filenameMaxLength) {
        this.filenameMaxLength = filenameMaxLength;
    }

    public String[] getAllowedSuffixNames() {
        return allowedSuffixNames;
    }

    public void setAllowedSuffixNames(String[] allowedSuffixNames) {
        this.allowedSuffixNames = allowedSuffixNames;
    }

    public String getBaseDirAvatar() {
        return baseDirAvatar;
    }

    public void setBaseDirAvatar(String baseDirAvatar) {
        this.baseDirAvatar = baseDirAvatar;
    }

    public String getBaseDirMarkdown() {
        return baseDirMarkdown;
    }

    public void setBaseDirMarkdown(String baseDirMarkdown) {
        this.baseDirMarkdown = baseDirMarkdown;
    }

    public String getBaseDirCover() {
        return baseDirCover;
    }

    public void setBaseDirCover(String baseDirCover) {
        this.baseDirCover = baseDirCover;
    }

    public String getBaseDirPicture() {
        return baseDirPicture;
    }

    public void setBaseDirPicture(String baseDirPicture) {
        this.baseDirPicture = baseDirPicture;
    }

    public String getMarkdownExtension() {
        return markdownExtension;
    }

    public void setMarkdownExtension(String markdownExtension) {
        this.markdownExtension = markdownExtension;
    }

    public String getDefaultImgType() {
        return defaultImgType;
    }

    public void setDefaultImgType(String defaultImgType) {
        this.defaultImgType = defaultImgType;
    }
}