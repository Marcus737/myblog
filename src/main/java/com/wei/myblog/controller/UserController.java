package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.dto.DisplayUser;
import com.wei.myblog.entity.User;
import com.wei.myblog.service.UserService;
import com.wei.myblog.utils.VerifyUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei
 */

@RestController
@RequestMapping("/myblog/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 获取用户总数和用户列表的集合
     * @param users
     * @return
     */
    private Map<String, Object> getResultListWithTotalNum(List<User> users){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalNum", userService.getTotalUserNum());
        resultMap.put("userList", users);
        return resultMap;
    }

    /**
     * 列出角色对应的用户
     * @param roleId
     * @return
     */
    @RequiresRoles("admin")
    @GetMapping("/listUserByRoleId")
    public Result listUserByRoleId(@RequestParam("roleId")String roleId){
        return Result.succeed(userService.listUserByRoleId(roleId));
    }


    /**
     * 后台管理系统的接口
     * @param begin 开始的下标
     * @param count 查询的数量
     * @param detail 是否详细查询
     * @return json响应
     */
    @RequiresRoles("admin")
    @GetMapping("/listUser")
    public Result listUser(@RequestParam("begin") Integer begin
            , @RequestParam("count") Integer count
            ,@RequestParam(value = "detail", required = false, defaultValue = "false")Boolean detail){
        List<User> users;
        if (detail){
            users = userService.listDetailUsers(begin, count);
            Map<String, Object> map = getResultListWithTotalNum(users);
            return Result.succeed(map);
        }
        users = userService.listUsers(begin, count);
        Map<String, Object> map = getResultListWithTotalNum(users);
        return Result.succeed(map);
    }

    /**
     * 用户接口
     * 查询根据用户名查询信息
     * @param username 用户名
     * @return json格式响应
     */
    @PostMapping("/getUser")
    public Result getUser(@RequestParam("username") String username){

        User user = userService.getUser(username);
        DisplayUser displayUser = new DisplayUser(user);
        return Result.succeed(displayUser);
    }

    /**
     * 用户接口/后台接口
     * @param user 用户信息
     * @return json格式响应
     */

    @PostMapping("/saveUser")
    public Result saveUser(User user, @RequestParam("avatarPath") String avatarPath) throws IOException {
        if (user.getEmail() == null){
            return Result.fail("邮箱不能为空");

        }/**
         * 验证用户的邮箱
         */
        boolean flag = VerifyUtils.verifyEmail(user.getEmail());
        if (!flag){
            return Result.fail("邮箱格式不正确");
        }
        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        user.setLoginTime(new Timestamp(System.currentTimeMillis()));
        user.setState(1);
        userService.saveUser(user, avatarPath);
        return Result.succeed();
    }

    /**
     * 用户接口/后台接口
     * 更新用户信息
     * @param user 用户信息
     * @return json格式响应
     */
    @PostMapping("/updateUser")
    @RequiresRoles(value = {"admin", "predestined"}, logical =  Logical.OR)
    @RequiresAuthentication
    public Result updateUser(User user
            , @RequestParam("avatarPath") String avatarPath) throws IOException {
        if (user.getUserId() == null) {
            return Result.fail("userId不能为空");
        }
        if (user.getEmail() == null){
            return Result.fail("邮箱不能为空");

        }/**
         * 验证用户的邮箱
         */
        boolean flag = VerifyUtils.verifyEmail(user.getEmail());
        if (!flag){
            return Result.fail("邮箱格式不正确");
        }
        userService.updateUser(user, avatarPath);
        return Result.succeed();
    }
    /**
     * 用户接口/后台接口
     * 删除用户
     * @return json格式响应
     */
    @GetMapping("/removeUser")
    @RequiresRoles("admin")
    @RequiresAuthentication
    public Result  removeUser(@RequestParam("userId") String userId){
        userService.removeUser(userId);
        return Result.succeed();
    }
}
