package com.wei.myblog.service.impl;

import com.wei.myblog.dao.ArticleCommentDao;
import com.wei.myblog.service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultArticleCommentService implements ArticleCommentService {

    @Autowired
    ArticleCommentDao articleCommentDao;

    /**
     * 文章与评论关联
     * @param articleId
     * @param commentId
     */
    @Override
    @Transactional
    public void associated(String articleId, String commentId) {
        articleCommentDao.associated(articleId, commentId);
    }

    /**
     * 删除文章与角色的关联
     * @param articleId
     * @param commentId
     */
    @Override
    @Transactional
    public void unAssociated(String articleId, String commentId) {
        articleCommentDao.unAssociated(articleId, commentId);
    }

    /**
     * 删除与该文章有关联的评论
     * @param articleId
     */
    @Override
    @Transactional
    public void removeAll(String articleId) {
        articleCommentDao.removeAll(articleId);
    }

    /**
     * 由评论id获取文章id
     * @param commentId
     * @return
     */
    @Override
    public String getArticleIdByCommentId(String commentId) {
        return articleCommentDao.getArticleIdByCommentId(commentId);
    }
}
