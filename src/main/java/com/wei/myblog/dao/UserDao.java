package com.wei.myblog.dao;

import com.wei.myblog.dto.DisplayUser;
import com.wei.myblog.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Repository
public interface UserDao {

    @Select("select user_id, username from user where user_id in (select user_id from user_role where role_id = #{roleId})")
    List<User> listUserByRoleId(String roleId);


    @Select("select count(*) from user")
    String getTotalUserNum();

    @Select("select user_id, username, avatar from user where user_id = (select user_id from user_comment where comment_id = #{commentId})")
    User getUserByCommentId(String commentId);

    /**
     *
     * @param username 用户名
     * @return 用户id
     */
    @Select("select user_id from user where username = #{username}")
    String getUserIdByUsername(String username);

    /**
     * 查询用户总数
     * @return 用户总数
     */
    @Select("select count(*) from user")
    Integer getTotalOfUser();

    /**
     * 查询指定数量的User
     * @param begin 查询的下标
     * @param count 查询的数量
     * @return 用户信息列表
     */
    @Select("select user_id, username, state, register_time, login_time, avatar, email from user limit #{begin}, #{count}")
    List<User> listUsers(int begin, int count);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    @Select("select user_id, password, username, state, register_time, login_time, avatar, email from user where username = #{username}")
    User getUser(String username);

    /**
     * 添加用户信息
     * @param user 用户信息
     * @return
     */
    @Insert("insert into user(username, password, state, register_time, login_time, avatar, email) values(#{username}, #{password}, #{state}, #{registerTime}, #{loginTime}, #{avatar}, #{email})")
    void saveUser(User user);

    /**
     * 根据用户名删除用户
     * @param username 用户名
     */
    @Delete("delete from user where username = #{username}")
    void removeUser(String username);

    /**
     * 使用trim就是为了删掉最后字段的“,”
     * 更新用户信息
     * @param user 用户信息
     */
    @Update({"<script>"
            + "update user set"
            + "<trim suffixOverrides=','>"
            + "<if test='username != null'> username = #{username},</if>"
            + "<if test='password != null'> password = #{password},</if>"
            + "<if test='state != null'> state = #{state},</if>"
            + "<if test='avatar != null'> avatar = #{avatar},</if>"
            + "<if test='email != null'> email = #{email},</if>"
            + "</trim>"
            + "where user_id = #{userId}"
            + "</script>"})
    void updateUser(User user);
}
