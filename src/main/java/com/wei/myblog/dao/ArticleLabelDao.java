package com.wei.myblog.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleLabelDao {

    @Insert("insert into article_label values(#{articleId}, #{labelId})")
    void associated(String articleId, String labelId);

    @Delete("delete from article_label where article_id = #{articleId} and label_id= #{labelId}")
    void unAssociated(String articleId, String labelId);

    @Delete("delete from article_label where article_id = #{articleId}")
    void removeAll(String articleId);
}
