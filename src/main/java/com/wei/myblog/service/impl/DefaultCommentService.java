package com.wei.myblog.service.impl;

import com.wei.myblog.dao.CommentDao;
import com.wei.myblog.dto.DisplayUser;
import com.wei.myblog.entity.Comment;
import com.wei.myblog.entity.User;
import com.wei.myblog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DefaultCommentService implements CommentService {

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserCommentService userCommentService;

    @Autowired
    ArticleCommentService articleCommentService;

    @Autowired
    UserService userService;

    private void getDetail(List<Comment> comments){
        for (Comment comment : comments){
            User userByCommentId = userService.getUserByCommentId(comment.getCommentId());
            /**
             *  由于删除用户时不会把用户的评论一起删除，所以会出现有评论没有所属用户的情况
             */
            if (userByCommentId == null){
                continue;
            }
            DisplayUser displayUser = new DisplayUser();
            displayUser.setUserId(userByCommentId.getUserId());
            displayUser.setUsername(userByCommentId.getUsername());
            displayUser.setAvatar(userByCommentId.getAvatar());
            comment.setDisplayUser(displayUser);
        }
    }

    @Override
    public String getTotalCommentNum() {
        return commentDao.getTotalCommentNum();
    }

    @Override
    public List<Comment> listComments(Integer begin, Integer count) {
        List<Comment> comments = commentDao.listComments(begin, count);
        getDetail(comments);
        return comments;
    }

    @Override
    public List<Comment> listCommentsByUserId(String userId) {
        return commentDao.listCommentsByUserId(userId);
    }

    @Override
    public List<Comment> listCommentsByArticleId(String articleId) {
        List<Comment> comments = commentDao.listCommentsByArticleId(articleId);
        getDetail(comments);
        return comments;
    }


    @Override
    @Transactional
    public void saveComment(Comment comment, String userId, String articleId) {
        //设置评论时间
        comment.setCommentTime(new Timestamp(System.currentTimeMillis()));

        commentDao.saveComment(comment);
        /**
         * 与用户关联
         */
        userCommentService.associated(userId, comment.getCommentId());
        /**
         * 与文章关联
         */
        articleCommentService.associated(articleId, comment.getCommentId());
    }

    @Override
    @Transactional
    public void updateComment(Comment comment) {
        /**
         * 设置更新时间
         */
        comment.setCommentTime(new Timestamp(System.currentTimeMillis()));
        commentDao.updateComment(comment);
    }

    @Override
    @Transactional
    public void removeCommentByCommentId(String commentId) {
        String userId = userCommentService.getUserIdByCommentId(commentId);
        String articleId = articleCommentService.getArticleIdByCommentId(commentId);
        /**
         *  解除与用户的关联
         */
        userCommentService.unAssociated(userId, commentId);
        /**
         *  解除与文章的关联
         */
        articleCommentService.unAssociated(articleId, commentId);
        commentDao.removeCommentByCommentId(commentId);
    }
}
