package com.wei.myblog.service.impl;

import com.wei.myblog.config.FileConfig;
import com.wei.myblog.dao.PictureDao;
import com.wei.myblog.entity.Picture;
import com.wei.myblog.service.ArticlePictureService;
import com.wei.myblog.service.FileService;
import com.wei.myblog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class DefaultPictureService implements PictureService {

    @Autowired
    PictureDao pictureDao;

    @Autowired
    ArticlePictureService articlePictureService;

    @Autowired
    FileService fileService;

    @Autowired
    FileConfig fileConfig;

    @Override
    public List<Picture> listPictures() {
        return pictureDao.listPictures();
    }

    @Override
    @Transactional
    public String savePicture(String articleId, MultipartFile pictureImg) throws IOException {
        String pictureUrl = fileService.upload(pictureImg, fileConfig.getBaseDirPicture() , fileConfig.getDefaultImgType());
        Picture picture = new Picture();
        picture.setPictureTime(new Timestamp(System.currentTimeMillis()));
        picture.setPictureUrl(pictureUrl);
        pictureDao.savePicture(picture);
        /**
         * 照片与用户关联起来
         */
        articlePictureService.associated(articleId, String.valueOf(picture.getPictureId()));
        return pictureUrl;
    }

    @Override
    @Transactional
    public void removePicture(String articleId) {
        List<String> pictureIds = articlePictureService.listPictureIdsByArticleId(articleId);
        for (String pictureId: pictureIds) {
            String path = pictureDao.getPictureUrlByPictureId(pictureId);
            fileService.removeFile(path);
        }
        pictureDao.removePicture(articleId);
    }
}
