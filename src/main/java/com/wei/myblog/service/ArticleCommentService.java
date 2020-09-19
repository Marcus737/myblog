package com.wei.myblog.service;

public interface ArticleCommentService {

    void associated(String articleId, String commentId);

    void unAssociated(String articleId, String commentId);

    void removeAll(String articleId);

    String getArticleIdByCommentId(String commentId);
}
