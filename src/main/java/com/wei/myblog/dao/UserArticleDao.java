package com.wei.myblog.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserArticleDao {

    @Insert("insert into user_article values(#{userId}, #{articleId})")
    void associatedUserWithArticle(String userId, String articleId);

    @Delete("delete from user_article where user_id = #{userId} and article_id = #{articleId}")
    void unAssociatedUserWithArticle(String userId, String articleId);

    @Delete("delete from user_article where user_id = #{userId}")
    void unAssociatedAll(String userId);

    @Select("select user_id from user_article where article_id = #{articleId}")
    String getUserIdByArticleId(String articleId);
}
