package com.wei.myblog.service.impl;

import com.wei.myblog.dao.RoleDao;
import com.wei.myblog.entity.Permission;
import com.wei.myblog.entity.Role;
import com.wei.myblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultRoleService implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    DefaultUserRoleService userRoleService;

    @Autowired
    DefaultRolePermissionService rolePermissionService;

    @Autowired
    DefaultPermissionService permissionService;

    @Override
    public Integer getTotalRoleNum() {
        return roleDao.getTotalRoleNum();
    }

    @Override
    public List<Role> listRolesByPermissionId(String permissionId) {
        return roleDao.listRolesByPermissionId(permissionId);
    }

    @Override
    public List<Role> listRoles(String userId) {
        return roleDao.listRoles(userId);
    }

    @Override
    public List<Role> listRoles() {
        return roleDao.getAllRoles();
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleDao.saveRole(role);
    }

    @Override
    @Transactional
    public void removeRole(String roleId) {

        /**
         *  解除角色与用户的关联
         */
        userRoleService.removeAll(roleId);
        /**
         *  解除角色与权限的关联
         */
        List<Permission> permissions = permissionService.listPermissions(roleId);
        for (Permission permission: permissions){
            rolePermissionService.removeAll(String.valueOf(permission.getPermissionId()));
        }
        roleDao.removeRole(roleId);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        roleDao.updateRole(role);
    }

    @Override
    public void addRoleToUser(String userId, String roleId) {
        userRoleService.associated(userId, roleId);
    }

    @Override
    public void removeRoleFromUser(String userId, String roleId) {
        userRoleService.unAssociation(userId, roleId);
    }
}
