package com.wei.myblog.service;

import com.wei.myblog.entity.Label;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LabelService {
    /**
     * 查询所有标签
     * @return 标签列表
     */
    List<Label> listLabels();

    String getLabelIdByLabelName(String labelName);

    /**
     * 添加标签
     * @param label 标签信息
     */
    void saveLabel(Label label);

    /**
     * 删除标签
     * @param labelId 标签id
     */
    void removeLabel(String labelId);

    void removeLabelIdFromArticleLabel(String labelId);
}
