package com.wei.myblog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "token-config")
public class TokenConfig {

    public Long expireTime;

    public Long refreshExpireTime;

    public String tokenSecret;

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Long getRefreshExpireTime() {
        return refreshExpireTime;
    }

    public void setRefreshExpireTime(Long refreshExpireTime) {
        this.refreshExpireTime = refreshExpireTime;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
