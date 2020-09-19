package com.wei.myblog.dao;

import com.wei.myblog.entity.Permission;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface PermissionDao {

    /**
     *
     * @param permissionName 权限名
     * @return 权限id
     */
    @Select("select permission_id from permission where permission_name = #{permissionName}")
    String getPermissionIdByPermissionName(String permissionName);

    /**
     * 查询所有权限
     * @return 权限列表
     */
    @Select("select permission_id, permission_name, permission_url from permission")
    List<Permission> getAllPermissions();

    /**
     * 查询角色所拥有的权限
     * @param roleId 角色id
     * @return 权限列表
     */
    @Select("select permission_id, permission_name, permission_url from permission where permission_id in (select permission_id from role_permission where role_id = #{roleId})")
    List<Permission> listPermissions(String roleId);

    /**
     * 添加权限
     * @param permission 权限信息
     * @return 是否成功
     */
    @Insert("insert into permission(permission_name, permission_url) values(#{permissionName}, #{permissionUrl})")
    void savePermission(Permission permission);

    /**
     * 根据权限名删除权限
     * @param permissionName 权限名
     * @return 是否成功
     */
    @Delete("delete from permission where permission_id = #{permissionId}")
    void removePermission(String permissionId);

    /**
     * 更新权限
     * @param permission 权限信息
     * @return 是否成功
     */
    @Update({"<script>"
            + "update permission set"
            + "<trim suffixOverrides=','>"
            + "<if test='permissionName != null'> permission_name = #{permissionName},</if>"
            + "<if test='permissionUrl != null'> permission_Url = #{permissionUrl},</if>"
            + "</trim>"
            + "where permission_id = #{permissionId}"
            + "</script>"})
    void updatePermission(Permission permission);
}
