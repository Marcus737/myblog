package com.wei.myblog.service.impl;

import com.wei.myblog.dao.ArticleLabelDao;
import com.wei.myblog.service.ArticleLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultArticleLabelService implements ArticleLabelService {

    @Autowired
    ArticleLabelDao articleLabelDao;

    @Override
    public void associated(String articleId, String labelId) {
        articleLabelDao.associated(articleId, labelId);
    }

    @Override
    public void unAssociated(String articleId, String labelId) {
        articleLabelDao.unAssociated(articleId, labelId);
    }

    @Override
    public void removeAll(String articleId) {
        articleLabelDao.removeAll(articleId);
    }

    @Override
    public void removeAllLabel(String articleId) {
        articleLabelDao.removeAllLabel(articleId);
    }
}
