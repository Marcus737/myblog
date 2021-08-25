package com.wei.myblog.service;

import com.wei.myblog.entity.Role;

import java.util.List;

public interface RoleService{

    Integer getTotalRoleNum();

    List<Role> listRolesByPermissionId(String permissionId);

    /**
     *
     * @param roleName
     * @return
     */
    String getRoleIdbByRoleName(String roleName);

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
     * @param roleName 角色名
     */
    void removeRole(String roleName);

    /**
     * 更新角色
     * @param role 角色信息
     */
    void updateRole(Role role);
}
