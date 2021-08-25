package com.wei.myblog.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RolePermissionDao {
    /**
     * 建立关联
     * @param roleId 角色id
     * @param permissionId 权限id
     * @return 是否成功
     */
    @Insert("insert into role_permission(role_id, permission_id) values(#{roleId}, #{permissionId})")
    void associated(String roleId, String permissionId);

    /**
     * 删除关联
     * @param roleId 角色id
     * @return 是否成功
     */
    @Delete("delete from role_permission where role_id = #{roleId} and permission_id = #{permissionId}")
    void unAssociation(String roleId, String permissionId);

    @Delete("delete from role_permission where role_id = #{roleId}")
    void removeAll(String roleId);
}
