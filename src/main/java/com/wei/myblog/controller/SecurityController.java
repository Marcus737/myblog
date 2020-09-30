package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
import com.wei.myblog.config.TokenConfig;
import com.wei.myblog.dto.DisplayUser;
import com.wei.myblog.entity.User;
import com.wei.myblog.service.UserService;
import com.wei.myblog.utils.RedisUtils;
import com.wei.myblog.utils.TokenUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


import java.security.SecureRandom;
import java.util.Random;

/**
 * 客户端向服务端请求，服务端返回salt，客户端传输的密码为md5(salt+md5(password))
 * 服务端保存的是密码是md5(password)
 * @author Administrator

 */

@RestController
@RequestMapping("/myblog/security")
public class SecurityController {

    @Autowired
    TokenConfig tokenConfig;

    @Autowired
    UserService userService;


    /**
     *     salt存放1分钟
      */
    private static final Integer KEEP_TIME = 60;

    public static final String DIVIDER = "+";

    private String getRandomSalt(){
        Random ranGen = new SecureRandom();
        byte[] aesKey = new byte[20];
        ranGen.nextBytes(aesKey);
        StringBuilder salt = new StringBuilder();
        for (byte b : aesKey) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                salt.append('0');
            }
            salt.append(hex);
        }
        return salt.toString();
    }

    private void setToken(String username, HttpServletResponse response){
        String refreshToken = TokenUtils.getToken(username, tokenConfig.refreshExpireTime, tokenConfig.getTokenSecret());
        String expireToken = TokenUtils.getToken(username, tokenConfig.expireTime, tokenConfig.getTokenSecret());
        String token = refreshToken + DIVIDER + expireToken;
        response.setHeader("token", token);
    }

    private DisplayUser setUser(String username){
        User user = userService.getUser(username);
        DisplayUser displayUser = new DisplayUser();
        displayUser.setUserId(user.getUserId());
        displayUser.setUsername(user.getUsername());
        displayUser.setAvatar(user.getAvatar());
        displayUser.setEmail(user.getEmail());
        return displayUser;
    }

    private String verifyUser(String username, String password){
        String salt = null;
        //先从redis中找salt
        if (RedisUtils.hasKey(username)){
            //获取salt
            salt = (String) RedisUtils.get(username);
            //把salt删除
            RedisUtils.del(username);
        }
        if (salt == null){
            return "请求失败, salt不存在";
        }
        User user = userService.getUser(username);
        if (user.getState() != 1){
            return "此账户已被冻结， 请联系管理员";
        }
        /**
         * 数据库存放的密码是md5后的密码
         */
        String key = DigestUtils.md5DigestAsHex((salt + user.getPassword()).getBytes()).toLowerCase();
        if (!password.equals(key)){
            return "用户名或密码错误";
        }
        return null;
    }

    /**
     * 获取随机盐
     */

    @PostMapping("/randomSalt")
    public Result randomSalt(@RequestParam("username") String username) {
        String id = userService.getUserIdByUsername(username);
        if (id == null){
            return Result.fail("用户名或密码错误");
        }
        //如果已经存在salt就删除，确保只有一个salt
        if (RedisUtils.hasKey(username)){
            RedisUtils.del(username);
        }
        String salt = getRandomSalt();
        //存放120秒
        RedisUtils.set(username, salt, KEEP_TIME);
        return Result.succeed(salt);
    }

    /**
     * 登录操作
     * @param username
     * @param password
     * @param response
     * @return
     */

    @PostMapping("/login")
    public Result login(@RequestParam(value = "username") String username
            , @RequestParam("password") String password
            , HttpServletResponse response) {
        try{
            String isAccess = verifyUser(username, password);
            if (isAccess != null){
                return Result.fail(isAccess);
            }
            /**
             * 验证成功后的其他操作
             */
            setToken(username, response);
            /**
             * 返回用户信息
             */
            DisplayUser displayUser = setUser(username);
            return Result.succeed(displayUser);
        }catch (Exception e){
            Result.fail("登录失败");
        }
        return Result.fail("登录失败");
    };

    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    public Result logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.succeed();
    }

}
