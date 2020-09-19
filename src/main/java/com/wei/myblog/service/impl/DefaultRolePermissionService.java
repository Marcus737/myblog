package com.wei.myblog.service.impl;

import com.wei.myblog.dao.RolePermissionDao;
import com.wei.myblog.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultRolePermissionService implements RolePermissionService {

    @Autowired
    RolePermissionDao rolePermissionDao;

    @Override
    public void associated(String roleId, String permissionId) {
        rolePermissionDao.associated(roleId, permissionId);
    }

    @Override
    public void unAssociation(String roleId, String permissionId) {
        rolePermissionDao.unAssociation(roleId, permissionId);
    }


    @Override
    public void removeAll(String permissionId) {
        rolePermissionDao.removeAll(permissionId);
    }
}
