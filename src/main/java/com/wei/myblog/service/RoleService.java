package com.wei.myblog.service;

import com.wei.myblog.entity.Role;

import java.util.List;

public interface RoleService{

    Integer getTotalRoleNum();

    List<Role> listRolesByPermissionId(String permissionId);


    /**
     * 查询y用户拥有的角色
     * @return 角色列表
     */
    List<Role> listRoles(String userId);

    /**
     * 查询所有角色
     * @return 角色列表
     */
    List<Role> listRoles();

    /**
     * 添加角色
     * @param role 角色信息
     */
    void saveRole(Role role);

    /**
     * 删除角色
     */
    void removeRole(String roleId);

    /**
     * 更新角色
     * @param role 角色信息
     */
    void updateRole(Role role);

    void addRoleToUser(String userId, String roleId);

    void removeRoleFromUser(String userId, String roleId);
}
