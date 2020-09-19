package com.wei.myblog.service;

import java.util.List;

public interface ArticlePictureService {

    void associated(String articleId, String pictureId);

    void unAssociated(String articleId, String pictureId);

    void removeAll(String articleId);

    List<String> listPictureIdsByArticleId(String articleId);

}
