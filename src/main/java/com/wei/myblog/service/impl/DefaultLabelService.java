package com.wei.myblog.service.impl;

import com.wei.myblog.dao.LabelDao;
import com.wei.myblog.entity.Label;
import com.wei.myblog.service.ArticleLabelService;
import com.wei.myblog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultLabelService implements LabelService {

    @Autowired
    LabelDao labelDao;

    @Autowired
    ArticleLabelService articleLabelService;

    @Override
    public List<Label> listLabels() {
        return labelDao.listLabels();
    }

    @Override
    public List<Label> listLabelsByArticleId(String articleId) {
        return labelDao.listLabelsByArticleId(articleId);
    }

    @Override
    @Transactional
    public void saveLabel(Label label) {
        labelDao.saveLabel(label);
    }

    @Override
    @Transactional
    public void removeLabel(String labelId) {
        /**
         *  解除所有与此标签关联的文章
         */
        articleLabelService.removeAll(labelId);
        labelDao.removeLabel(labelId);
    }
}
