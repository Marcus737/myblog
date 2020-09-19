package com.wei.myblog.service;

import com.wei.myblog.entity.Picture;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface PictureService {
    /**
     * 查询所有的图片
     * @return 图片列表
     */
    List<Picture> listPictures();

    /**
     * 保存图片信息
     */
    String savePicture(String articleId, MultipartFile pictureImg) throws IOException;

    /**
     * 根据图片id删除图片
     */
    void removePicture(String articleId);
}
