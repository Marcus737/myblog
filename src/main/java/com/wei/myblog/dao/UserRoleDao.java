package com.wei.myblog.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Mapper
@Repository
public interface UserRoleDao {

    /**
     * 将user和role关联起来
     * @param userId 用户id
     * @param roleId 角色id
     */
    @Insert("insert into user_role(user_id, role_id) values(#{userId}, #{roleId})")
    void associated(String userId, String roleId);

    /**
     * 根据userId删除关联
     * @param userId 用过id
     */
    @Delete("delete from user_role where user_id = #{userId} and role_id = #{roleId}")
    void unAssociation(String userId, String roleId);

    /**
     * @param userId
     */
    @Delete("delete from user_role where user_id = #{userId}")
    void removeAll(String userId);
}
