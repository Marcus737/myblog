package com.wei.myblog.dao;

import com.wei.myblog.entity.Label;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LabelDao {

    /**
     * 查询所有标签
     * @return 标签列表
     */
    @Select("select label_id, label_name from label")
    List<Label> listLabels();

    @Select("select label_id from label where label_name = #{labelName}")
    String getLabelIdByLabelName(String labelName);

    @Select("select label_id, label_name from label where label_id in (select label_id from article_label where article_id = #{articleId})")
    List<Label> listLabelsByArticleId(String articleId);

    /**
     * 添加标签
     * @param label 标签信息
     */
    @Insert("insert into label(label_name) values(#{labelName})")
    void saveLabel(Label label);

    /**
     * 删除标签
     * @param labelId 标签id
     */
    @Delete("delete from label where label_id = #{labelId}")
    void removeLabel(String labelId);

    @Delete("delete from article_label where label_id = #{labelId}")
    void removeLabelIdFromArticleLabel(String labelId);
}
