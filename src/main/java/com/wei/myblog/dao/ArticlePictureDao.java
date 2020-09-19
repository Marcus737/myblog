package com.wei.myblog.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticlePictureDao {

    @Insert("insert into article_picture values(#{articleId}, #{pictureId})")
    void associated(String articleId, String pictureId);

    @Delete("delete from article_picture where article_id = #{articleId} and comment_id = #{pictureId}")
    void unAssociated(String articleId, String pictureId);

    @Delete("delete from article_picture where article_id = #{articleId}")
    void removeAll(String articleId);

    @Select("select picture_id from article_picture where article_id = #{articleId}")
    List<String> listPictureIdsByArticleId(String articleId);
}
