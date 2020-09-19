package com.wei.myblog.dao;

import com.wei.myblog.entity.Picture;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PictureDao {

    /**
     * 查询所有的图片
     * @return 图片列表
     */
    @Select("select picture_id, picture_time, picture_url from picture")
    List<Picture> listPictures();

    /**
     * 保存图片信息
     * @param picture 图片信息
     */
    @Insert("insert into picture(picture_time, picture_url) values(#{pictureTime}, #{pictureUrl})")
    @Options(useGeneratedKeys = true, keyProperty = "pictureId", keyColumn = "picture_id")
    void savePicture(Picture picture);

    /**
     * 根据图片id删除图片
     * @param pictureId 图片id
     */
    @Delete("delete from picture where picture_id in (select picture_id from article_picture where article_id = #{articleId})")
    void removePicture(String articleId);

    @Select("select picture_url from picture where picture_id = #{pictureId}")
    String getPictureUrlByPictureId(String pictureId);
}
