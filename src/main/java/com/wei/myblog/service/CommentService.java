package com.wei.myblog.service;

import com.wei.myblog.entity.Comment;

import java.util.List;

public interface CommentService {
    String getTotalCommentNum();

    /**
     * 查询指定数量的评论
     * @param begin 开始的评论下标
     * @param count 查询数量
     * @return 评论列表
     */
    List<Comment> listComments(Integer begin, Integer count);

    /**
     * 查询用户所有的评论评论
     * @param userId 用户id
     * @return 评论列表
     */
   List<Comment> listCommentsByUserId(String userId);

    /**
     * 查询文章所属的评论
     * @param articleId 评论id
     * @return 评论列表
     */
    List<Comment> listCommentsByArticleId(String articleId);


    /**
     * 保存评论
     * @param comment 评论信息
     */
    void saveComment(Comment comment, String userId, String articleId);

    /**
     * 更新评论
     * @param comment 评论信息
     */
    void updateComment(Comment comment);

    /**
     * 根据评论id删除评论
     * @param commentId 评论id
     */
    void removeCommentByCommentId(String commentId);
}
