package com.wei.myblog.service.impl;

import com.wei.myblog.dao.LabelDao;
import com.wei.myblog.entity.Label;
import com.wei.myblog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultLabelService implements LabelService {

    @Autowired
    LabelDao labelDao;

    @Override
    public List<Label> listLabels() {
        return labelDao.listLabels();
    }

    @Override
    public String getLabelIdByLabelName(String labelName) {
        return labelDao.getLabelIdByLabelName(labelName);
    }

    @Override
    @Transactional
    public void saveLabel(Label label) {
        labelDao.saveLabel(label);
    }

    @Override
    @Transactional
    public void removeLabel(String labelId) {
        labelDao.removeLabelIdFromArticleLabel(labelId);
        labelDao.removeLabel(labelId);
    }

    @Override
    public void removeLabelIdFromArticleLabel(String labelId) {

    }
}
