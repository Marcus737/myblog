package com.wei.myblog.service;

public interface UserRoleService {

    void associated(String userId, String roleId);

    void unAssociation(String userId, String roleId);

    void removeAll(String roleId);

    void removeAllByUserId(String userId);
}
