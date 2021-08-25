package com.wei.myblog.dao;

import com.wei.myblog.entity.Picture;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
    void savePicture(Picture picture);

    /**
     * 根据图片id删除图片
     * @param pictureId 图片id
     */
    @Delete("delete from picture where picture_id = #{pictureId}")
    void removePicture(String pictureId);
}
