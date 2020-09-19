package com.wei.myblog.dao;

import com.wei.myblog.entity.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleDao {

    @Select("select count() * from role")
    Integer getTotalRoleNum();

    @Select("select role_id, role_name from role where role_id in (select role_id from role_permission where permission_id = #{permissionId})")
    List<Role> listRolesByPermissionId(String permissionId);

    /**
     * 查询y用户拥有的角色
     * @return 角色列表
     */
    @Select("select role_id, role_name from role where role_id in (select role_id from user_role where user_id = #{userId})")
    List<Role> listRoles(String userId);

    /**
     * 查询所有角色
     * @return 角色列表
     */
    @Select("select role_id, role_name from role")
    List<Role> getAllRoles();

    /**
     * 添加角色
     * @param role 角色信息
     * @return 是否成功
     */
    @Insert("insert into role(role_name) values(#{roleName})")
    void saveRole(Role role);

    /**
     * 删除角色
     * @return 是否成功
     */
    @Delete("delete from role where role_id = #{roleId}")
    void removeRole(String roleId);

    /**
     * 更新角色
     * @param role 角色信息
     * @return 是否成功
     */
    @Update({"<script>"
            + "update role set"
            + "<trim suffixOverrides=','>"
            + "<if test='roleName != null'> role_name = #{roleName},</if>"
            + "</trim>"
            + "where role_id = #{roleId}"
            + "</script>"})
    void updateRole(Role role);
}
