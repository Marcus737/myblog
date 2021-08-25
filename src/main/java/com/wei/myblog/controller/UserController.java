package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.dto.DisplayUser;
import com.wei.myblog.entity.User;
import com.wei.myblog.service.AssociationBuilder;
import com.wei.myblog.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/myblog/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AssociationBuilder associationBuilder;

    /**
     * role表的predestined的id
     */
    private static final String ROLE_ID_PREDESTINED = "2";

    private Map<String, Object> getResultListWithTotalNum(List<User> users){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalNum", userService.getTotalUserNum());
        resultMap.put("userList", users);
        return resultMap;
    }

    @RequiresRoles("admin")
    @GetMapping("/listUserByRoleId")
    public Result listUserByRoleId(@RequestParam("roleId")String roleId){
        return Result.succeed(userService.listUserByRoleId(roleId));
    }

    @GetMapping("/logout")
    public Result logout(ServletRequest request, ServletResponse response){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.succeed();
    }

    @GetMapping("/isLoginUser")
    public Result isLoginUser(){
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        Map<String, Boolean> map = new HashMap<>();
        map.put("isAuthenticated", authenticated);
        return Result.succeed(map);
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
    @GetMapping("/getUser")
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
    @CrossOrigin
    public Result saveUser(User user){
        user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
        user.setLoginTime(new Timestamp(System.currentTimeMillis()));
        user.setState(1);
        userService.saveUser(user);
        /**
         * 默认给新用户关联predestined
         */
        associationBuilder.associateUserWithRole(userService.getUserIdByUsername(user.getUsername()), ROLE_ID_PREDESTINED);
        return Result.succeed();
    }

    @PostMapping("/simpleUpdate")
    @RequiresRoles("predestined")
    @RequiresAuthentication
    public Result simpleUpdate(Integer userId, String username, String password, String email){
        if (userId == null){
            return Result.fail("id不能为空");
        }
        User user = new User();
        user.setUserId(userId);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        userService.updateUser(user);
        return Result.succeed();
    }

    /**
     * 用户接口/后台接口
     * 更新用户信息
     * @param user 用户信息
     * @return json格式响应
     */
    @PostMapping("/updateUser")
    @RequiresRoles("admin")
    @RequiresAuthentication
    public Result updateUser(User user){
        if (user.getUserId() == null){
            return Result.fail("id不能为空");
        }
        userService.updateUser(user);
        return Result.succeed();
    }

    /**
     * 用户接口/后台接口
     * 删除用户
     * @param username 用户名
     * @return json格式响应
     */
    @GetMapping("/removeUser")
    @RequiresRoles("admin")
    @RequiresAuthentication
    public Result  removeUser(@RequestParam("username") String username){
        String userId = userService.getUserIdByUsername(username);
        this.associationBuilder.removeAllFromUserId(userId);
        userService.removeUser(username);
        return Result.succeed();
    }
}
