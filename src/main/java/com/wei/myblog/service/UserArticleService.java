package com.wei.myblog.service;

public interface UserArticleService {

    String getUserIdByArticleId(String articleId);

    void associatedUserWithArticle(String userId, String articleId);

    void unAssociatedUserWithArticle(String userId, String articleId);

    void removeAll(String userId);
}
