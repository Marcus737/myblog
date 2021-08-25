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
}