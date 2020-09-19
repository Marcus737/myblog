package com.wei.myblog.service.impl;

import com.wei.myblog.dao.UserArticleDao;
import com.wei.myblog.dao.UserCommentDao;
import com.wei.myblog.service.UserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultUserCommentService implements UserCommentService {

    @Autowired
    UserCommentDao userCommentDao;

    @Override
    public void associated(String userId, String commentId) {
        userCommentDao.associated(userId, commentId);
    }

    @Override
    public void unAssociated(String userId, String commentId) {
        userCommentDao.unAssociated(userId, commentId);
    }

    @Override
    public String getUserIdByCommentId(String commentId) {
        return userCommentDao.getUserIdByCommentId(commentId);
    }

    @Override
    public void removeAll(String userId) {
        userCommentDao.removeAll(userId);
    }
}
