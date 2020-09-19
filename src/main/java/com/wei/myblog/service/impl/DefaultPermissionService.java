package com.wei.myblog.service.impl;

import com.wei.myblog.dao.PermissionDao;
import com.wei.myblog.entity.Permission;
import com.wei.myblog.service.PermissionService;
import com.wei.myblog.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultPermissionService implements PermissionService {

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    RolePermissionService rolePermissionService;

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
        permissionDao.savePermission(permission);
    }

    @Override
    @Transactional
    public void removePermission(String permissionId) {
        /**
         * 解除与角色的关联
         */
        rolePermissionService.removeAll(permissionId);
        permissionDao.removePermission(permissionId);
    }

    @Override
    @Transactional
    public void updatePermission(Permission permission) {
        permissionDao.updatePermission(permission);
    }

    public void addPermissionToRole(String permissionId, List<String> roleIdList){
        for (String roleId: roleIdList) {
            rolePermissionService.associated(roleId, permissionId);
        }
    }

    public void removePermissionFromRole(String permissionId, List<String> roleIdList){
        for (String roleId: roleIdList) {
            rolePermissionService.unAssociation(roleId, permissionId);
        }
    }
}
