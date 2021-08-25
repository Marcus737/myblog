package com.wei.myblog.service.impl;

import com.wei.myblog.dao.CommentDao;
import com.wei.myblog.dto.DisplayUser;
import com.wei.myblog.entity.Comment;
import com.wei.myblog.entity.User;
import com.wei.myblog.service.ArticleService;
import com.wei.myblog.service.AssociationBuilder;
import com.wei.myblog.service.CommentService;
import com.wei.myblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultCommentService implements CommentService {

    private static final String DIVISION_SIGN = "+";

    @Autowired
    CommentDao commentDao;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    AssociationBuilder associationBuilder;

    private void getDetail(List<Comment> comments){
        for (Comment comment : comments){
            User userByCommentId = userService.getUserByCommentId(comment.getCommentId());
            DisplayUser displayUser = new DisplayUser();
            displayUser.setUserId(userByCommentId.getUserId());
            displayUser.setUsername(userByCommentId.getUsername());
            displayUser.setAvatar(userByCommentId.getAvatar());
            comment.setCommentContent(comment.getCommentContent().substring(comment.getCommentContent().indexOf(DIVISION_SIGN) + 1));
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
    public String getCommentIdByCommentContent(String commentContent) {
        String content = commentDao.getCommentIdByCommentContent(commentContent);
        //第一个加号出现的索引
        int i = content.indexOf(DIVISION_SIGN);
        return content.substring(i + 1);
    }

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        String uuid = UUID.randomUUID().toString();
        String uuidContent = uuid + DIVISION_SIGN + comment.getCommentContent();
        comment.setCommentContent(uuidContent);
        commentDao.saveComment(comment);
    }

    @Override
    @Transactional
    public void updateComment(Comment comment) {
        commentDao.updateComment(comment);
    }

    @Override
    public void removeCommentByCommentId(String commentId) {

        commentDao.removeCommentByCommentId(commentId);
    }
}
