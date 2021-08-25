package com.wei.myblog.service.impl;

import com.wei.myblog.dao.RoleDao;
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

    @Override
    public Integer getTotalRoleNum() {
        return roleDao.getTotalRoleNum();
    }

    @Override
    public List<Role> listRolesByPermissionId(String permissionId) {
        return roleDao.listRolesByPermissionId(permissionId);
    }

    @Override
    public String getRoleIdbByRoleName(String roleName) {
        return roleDao.getRoleIdByRoleName(roleName);
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
        if (role == null){
            throw new NullPointerException("role对象不能为空");
        }
        roleDao.saveRole(role);
    }

    @Override
    @Transactional
    public void removeRole(String roleName) {
        roleDao.removeRole(roleName);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        if (role == null){
            throw new NullPointerException("role对象不能为空");
        }
        roleDao.updateRole(role);
    }
}
