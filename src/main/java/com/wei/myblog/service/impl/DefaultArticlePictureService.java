package com.wei.myblog.service.impl;

import com.wei.myblog.dao.ArticlePictureDao;
import com.wei.myblog.service.ArticlePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultArticlePictureService implements ArticlePictureService {

    @Autowired
    ArticlePictureDao articlePictureDao;

    @Override
    public void associated(String articleId, String pictureId) {
        articlePictureDao.associated(articleId, pictureId);
    }

    @Override
    public void unAssociated(String articleId, String pictureId) {
        articlePictureDao.unAssociated(articleId, pictureId);
    }

    @Override
    public void removeAll(String articleId) {
        articlePictureDao.removeAll(articleId);
    }

    @Override
    public List<String> listPictureIdsByArticleId(String articleId) {
        return articlePictureDao.listPictureIdsByArticleId(articleId);
    }
}
