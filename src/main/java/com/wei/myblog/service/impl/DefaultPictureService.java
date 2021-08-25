package com.wei.myblog.service.impl;

import com.wei.myblog.dao.PictureDao;
import com.wei.myblog.entity.Picture;
import com.wei.myblog.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultPictureService implements PictureService {

    @Autowired
    PictureDao pictureDao;

    @Override
    public List<Picture> listPictures() {
        return pictureDao.listPictures();
    }

    @Override
    @Transactional
    public void savePicture(Picture picture) {
        pictureDao.savePicture(picture);
    }

    @Override
    @Transactional
    public void removePicture(String pictureId) {
        pictureDao.removePicture(pictureId);
    }
}
