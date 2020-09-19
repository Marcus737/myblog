package com.wei.myblog.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleCommentDao {
    @Insert("insert into article_comment values(#{articleId}, #{commentId})")
    void associated(String articleId, String commentId);

    @Delete("delete from article_comment where article_id = #{articleId} and comment_id = #{commentId}")
    void unAssociated(String articleId, String commentId);

    @Delete("delete from article_comment where article_id = #{articleId}")
    void removeAll(String articleId);

    @Select("select article_id from article_comment where comment_id = #{commentId}")
    String getArticleIdByCommentId(String commentId);
}
