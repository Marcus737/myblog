package com.wei.myblog.service.impl;

import com.wei.myblog.config.FileConfig;
import com.wei.myblog.dao.UserDao;
import com.wei.myblog.entity.Permission;
import com.wei.myblog.entity.Role;
import com.wei.myblog.entity.User;

import com.wei.myblog.service.*;
import com.wei.myblog.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DefaultUserService implements UserService {

    private static final Integer minIndex = 0;
    private static final Integer maxCount = 20;

    @Value("${role.defaultRoleId}")
    private String ROLE_ID_PREDESTINED;

    @Autowired
    UserDao userDao;

    @Autowired
    FileConfig fileConfig;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    CommentService commentService;

    @Autowired
    ArticleService articleService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserArticleService userArticleService;

    @Autowired
    UserCommentService userCommentService;

    @Autowired
    FileService fileService;

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
            throw new IllegalArgumentException("查询数量count过大，限定小于20");
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
            throw new IllegalArgumentException("用户不存在");
        }
        return user;
    }

    @Override
    public User getDetailUser(String username) {
        User user = userDao.getUser(username);
        getDetail(user);
        return user;
    }


    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void saveUser(User user, String avatarPath) {
        user.setAvatar(avatarPath);
        userDao.saveUser(user);
        /**
         *  给用户绑定默认角色
         */
        String userId = userDao.getUserIdByUsername(user.getUsername());
        userRoleService.associated(userId, ROLE_ID_PREDESTINED);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void removeUser(String userId) {

        /**
         *  解除用户与角色的关联
         */
        userRoleService.removeAllByUserId(userId);
        /**
         *  解除用户与文章的关联
         */
        userArticleService.removeAll(userId);
        /**
         *  解除用户与评论的关联
         */
        userCommentService.removeAll(userId);
        userDao.removeUser(userId);
    }

    @Override
    @Transactional(rollbackFor = IllegalArgumentException.class)
    public void updateUser(User user, String avatarPath) {
        user.setAvatar(avatarPath);
        userDao.updateUser(user);
    }
}
