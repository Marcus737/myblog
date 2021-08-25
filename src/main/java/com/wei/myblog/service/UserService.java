package com.wei.myblog.service;

import com.wei.myblog.entity.User;

import java.util.List;

public interface UserService {

    List<User> listUserByRoleId(String roleId);

    String getTotalUserNum();

    User getUserByCommentId(String commentId);

    /**
     *
     * @param username 用户名
     * @return 用户id
     */
    String getUserIdByUsername(String username);

    /**
     * 查询指定数量的User
     * @param begin 查询的下标
     * @param count 查询的数量
     * @return 用户信息列表
     */
    List<User> listUsers(int begin, int count);

    /**
     * 详细的user列表
     * @param begin 查询的下标
     * @param count 查询的数量
     * @return 用户信息列表
     */
    List<User> listDetailUsers(int begin, int count);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User getUser(String username);

    /**
     * 详细的user
     * @param username 用户名
     * @return 用户信息
     */
    User getDetailUser(String username);

    /**
     * 添加用户信息
     * @param user 用户信息
     */
    void saveUser(User user);

    /**
     * 删除用户
     * @param username 用户名
     */
    void removeUser(String username);

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 跟新是否成功
     */
    void updateUser(User user);
}
