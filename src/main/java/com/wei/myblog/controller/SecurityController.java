package com.wei.myblog.controller;

import com.wei.myblog.common.Result;
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
 */

@RestController
@RequestMapping("/myblog/security")
public class SecurityController {

    @Autowired
    UserService userService;

    private static final Integer KEEP_TIME = 120;

    private String getRandomSalt(){
        Random ranGen = new SecureRandom();
        byte[] aesKey = new byte[20];
        ranGen.nextBytes(aesKey);
        StringBuffer salt = new StringBuffer();
        for (int i = 0; i < aesKey.length; i++) {
            String hex = Integer.toHexString(0xff & aesKey[i]);
            if (hex.length() == 1)
                salt.append('0');
            salt.append(hex);
        }
        return salt.toString();
    }

    @GetMapping("/randomSalt")
    public Result randomSalt(@RequestParam("username") String username){
        String id = userService.getUserIdByUsername(username);
        if (id == null){
            return Result.fail("无此用户");
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


    @GetMapping("/login")
    public Result login(@RequestParam(value = "username") String username
            , @RequestParam("password") String password
            , HttpServletResponse response) {
        String salt = null;
        //先从redis中找salt
        if (RedisUtils.hasKey(username)){
            //获取salt
            salt = (String) RedisUtils.get(username);
            //把salt删除
            RedisUtils.del(username);
        }
        if (salt == null){
            return Result.fail("请求失败, salt不存在");
        }
        User user = userService.getUser(username);
        /**
         * 数据库存放的密码是md5后的密码
         */

        String key = DigestUtils.md5DigestAsHex((salt + user.getPassword()).getBytes()).toLowerCase();
        if (!password.equals(key)){
            return Result.fail("用户名或密码错误");
        }

        /**
         * 登录成功后的其他操作
         */

        String refreshToken = TokenUtils.getToken(username, TokenUtils.REFRESH_EXPIRE_TIME);
        String expireToken = TokenUtils.getToken(username, TokenUtils.EXPIRE_TIME);
        response.setHeader("RefreshToken", refreshToken);
        response.setHeader("ExpireToken", expireToken);
        return Result.succeed();
    };

    @GetMapping("/logout")
    public Result logout(@RequestParam(value = "username") String username){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        if (RedisUtils.hasKey(username)){
            RedisUtils.del(username);
        }
        return Result.succeed();
    }

}
