package com.wei.myblog.service;

public interface RolePermissionService {
    void associated(String roleId, String permissionId);

    void unAssociation(String roleId, String permissionId);

    void removeAll(String permissionId);
}
