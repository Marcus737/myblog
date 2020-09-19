package com.wei.myblog.service.impl;

import com.wei.myblog.dao.UserArticleDao;
import com.wei.myblog.dao.UserDao;
import com.wei.myblog.service.UserArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultUserArticleService implements UserArticleService {

    @Autowired
    UserArticleDao userArticleDao;

    @Override
    public String getUserIdByArticleId(String articleId) {
        return userArticleDao.getUserIdByArticleId(articleId);
    }

    @Override
    @Transactional
    public void associatedUserWithArticle(String userId, String articleId) {
        userArticleDao.associatedUserWithArticle(userId, articleId);
    }

    @Override
    @Transactional
    public void unAssociatedUserWithArticle(String userId, String articleId) {
        userArticleDao.unAssociatedUserWithArticle(userId, articleId);
    }

    @Override
    public void removeAll(String userId) {
        userArticleDao.unAssociatedAll(userId);
    }
}
