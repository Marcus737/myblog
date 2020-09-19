package com.wei.myblog.dao;

import com.wei.myblog.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentDao {

    @Select("select count(*) from comment")
    String getTotalCommentNum();

    /**
     * 查询指定数量的评论
     * @param begin 开始的评论下标
     * @param count 查询数量
     * @return 评论列表
     */
    @Select("select comment_id, comment_time, comment_content from comment limit #{begin}, #{count}")
    List<Comment> listComments(Integer begin, Integer count);

    /**
     * 查询用户所有的评论评论
     * @param userId 用户id
     * @return 评论列表
     */
    @Select("select comment_id, comment_time, comment_content from comment where comment_id in (select comment_id from user_comment where user_id = #{userId})")
    List<Comment> listCommentsByUserId(String userId);

    /**
     * 查询文章所属的评论
     * @param articleId 评论id
     * @return 评论列表
     */
    @Select("select comment_id, comment_time, comment_content from comment where comment_id in (select comment_id from article_comment where article_id = #{articleId}) order by comment_time desc")
    List<Comment> listCommentsByArticleId(String articleId);

    /**
     * 保存评论
     * @param comment 评论信息
     */
    @Insert("insert into comment(comment_time, comment_content) values(#{commentTime}, #{commentContent})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId", keyColumn = "comment_id")
    void saveComment(Comment comment);

    /**
     * 更新评论
     * @param comment 评论信息
     */
    @Update({"<script>"
            + "update comment set"
            + "<trim suffixOverrides=','>"
            + "<if test='commentTime != null'> comment_time = #{commentTime},</if>"
            + "<if test='commentContent != null'> comment_content = #{commentContent},</if>"
            + "</trim>"
            + "where comment_id = #{commentId}"
            + "</script>"})
    void updateComment(Comment comment);

    /**
     * 根据评论id删除评论
     * @param commentId 评论id
     */
    @Delete("delete from comment where comment_id = #{commentId}")
    void removeCommentByCommentId(String commentId);
}
