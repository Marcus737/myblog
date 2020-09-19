package com.wei.myblog.service;

public interface UserCommentService {

    void associated(String userId, String commentId);

    void unAssociated(String userId, String commentId);

    String getUserIdByCommentId(String commentId);

    void removeAll(String userId);
}
