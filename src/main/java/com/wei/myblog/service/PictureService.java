package com.wei.myblog.service;

import com.wei.myblog.entity.Picture;


import java.util.List;

public interface PictureService {
    /**
     * 查询所有的图片
     * @return 图片列表
     */
    List<Picture> listPictures();

    /**
     * 保存图片信息
     * @param picture 图片信息
     */
    void savePicture(Picture picture);

    /**
     * 根据图片id删除图片
     * @param pictureId 图片id
     */
    void removePicture(String pictureId);
}
