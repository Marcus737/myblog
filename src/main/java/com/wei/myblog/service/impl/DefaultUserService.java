package com.wei.myblog.service.impl;

import com.wei.myblog.dao.UserDao;
import com.wei.myblog.entity.Permission;
import com.wei.myblog.entity.Role;
import com.wei.myblog.entity.User;
import com.wei.myblog.exception.NotExistException;
import com.wei.myblog.exception.ParameterOversizeException;
import com.wei.myblog.service.PermissionService;
import com.wei.myblog.service.RoleService;
import com.wei.myblog.service.UserService;
import com.wei.myblog.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private static final Integer minIndex = 0;
    private static final Integer maxCount = 20;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    private void getDetail(User user) {
        String userId = user.getUserId() + "";
        String roleId;
        List<Role> roles = roleService.listRoles(userId);
        for (Role role: roles){
            roleId = role.getRoleId() + "";
            List<Permission> permissions = permissionService.listPermissions(roleId);
            role.setPermissions(permissions);
        }
        user.setRoles(roles);
    }

    @Override
    public List<User> listUserByRoleId(String roleId) {
        return userDao.listUserByRoleId(roleId);
    }

    @Override
    public String getTotalUserNum() {
        return userDao.getTotalUserNum();
    }

    @Override
    public User getUserByCommentId(String commentId) {
        return userDao.getUserByCommentId(commentId);
    }

    @Override
    public String getUserIdByUsername(String username) {
        return userDao.getUserIdByUsername(username);
    }

    @Override
    public List<User> listUsers(int begin, int count) {
        if (begin < minIndex){
            throw new IllegalArgumentException("begin不能小于0");
        }
        if (count > maxCount){
            throw new ParameterOversizeException("查询数量count过大，限定小于20");
        }
        Integer totalUserNum = Integer.valueOf(userDao.getTotalUserNum());
        if (begin >= totalUserNum){
            throw new IllegalArgumentException("已经没有数据了");
        }
        if(begin + count > totalUserNum){
            count = totalUserNum - begin;
        }

        Integer expectTotal = begin + count;
        Integer inFactTotal = userDao.getTotalOfUser();
        if (expectTotal > inFactTotal){
            count = inFactTotal - begin;
        }
        return userDao.listUsers(begin, count);
    }

    @Override
    public List<User> listDetailUsers(int begin, int count) {
        List<User> users = listUsers(begin, count);
        for(User user: users){
            getDetail(user);
        }
        return users;
    }

    @Override
    public User getUser(String username) {
        User user = userDao.getUser(username);
        if (user == null){
            throw new NotExistException("用户不存在");
        }
        return user;
    }

    @Override
    public User getDetailUser(String username) {
        User user = getUser(username);
        getDetail(user);
        return user;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        if (user == null){
            throw new NullPointerException("user对象不能为空");
        }
        /**
         * 验证用户的邮箱
         */
        if (user.getEmail() != null){
            VerifyUtils.verifyEmail(user.getEmail());
        }
        userDao.saveUser(user);
    }

    @Override
    @Transactional
    public void removeUser(String username) {
        userDao.removeUser(username);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        /**
         * 验证用户的邮箱
         */
        if (user.getEmail() != null){
            VerifyUtils.verifyEmail(user.getEmail());
        }
        userDao.updateUser(user);
    }
}
