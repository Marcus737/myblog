package com.wei.myblog.service.impl;

import com.wei.myblog.dao.PermissionDao;
import com.wei.myblog.entity.Permission;
import com.wei.myblog.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultPermissionService implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Override
    public String getPermissionIdByPermissionName(String permissionName) {
        return permissionDao.getPermissionIdByPermissionName(permissionName);
    }

    @Override
    public List<Permission> listPermissions() {
        return permissionDao.getAllPermissions();
    }

    @Override
    public List<Permission> listPermissions(String roleId) {
        return permissionDao.listPermissions(roleId);
    }

    @Override
    @Transactional
    public void savePermission(Permission permission) {
        if (permission == null){
            throw new NullPointerException("permission对象不能为空");
        }
        permissionDao.savePermission(permission);
    }

    @Override
    public void removePermission(String permissionName) {
        permissionDao.removePermission(permissionName);
    }

    @Override
    @Transactional
    public void updatePermission(Permission permission) {
        if (permission == null){
            throw new NullPointerException("permission对象不能为空");
        }
        permissionDao.updatePermission(permission);
    }
}
