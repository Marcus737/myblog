package com.wei.myblog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface ArticleLabelService {

    void associated(String articleId, String labelId);

    void unAssociated(String articleId, String labelId);

    void removeAll(String labelId);

    void removeAllLabel(String articleId);
}
