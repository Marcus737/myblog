package com.wei.myblog.service;

import com.wei.myblog.entity.Permission;

import java.util.List;

public interface PermissionService {

    /**
     *
     * @param permissionId
     * @return
     */
    String getPermissionIdByPermissionName(String permissionName);

    /**
     * 查询所有权限
     * @return 权限列表
     */
    List<Permission> listPermissions();

    /**
     * 查询角色所拥有的权限
     * @param roleId 角色id
     * @return 权限列表
     */
    List<Permission> listPermissions(String roleId);

    /**
     * 添加权限
     * @param permission 权限信息
     */
    void savePermission(Permission permission);

    /**
     * 根据权限名删除权限
     * @param permissionName 权限名
     */
    void removePermission(String permissionName);

    /**
     * 更新权限
     * @param permission 权限信息
     */
    void updatePermission(Permission permission);
}
