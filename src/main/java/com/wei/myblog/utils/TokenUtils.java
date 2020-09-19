package com.wei.myblog.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wei.myblog.config.TokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * token工具类
 */
public class TokenUtils {

    public static String getToken(String username, Long continueTime, String tokenSecret){
        String token;
        try {
            /**
             * 过期时间
             */
            long curTime = System.currentTimeMillis();
            Date expireTime = new Date(curTime + continueTime);
            token = JWT.create()
                    .withIssuer("auth0") // 发行人
                    .withClaim("username", username) // 存放数据
                    .withClaim("currentTime", curTime + continueTime)
                    .withExpiresAt(expireTime) // 过期时间
                    .sign(Algorithm.HMAC256(tokenSecret));
        } catch (IllegalArgumentException | JWTCreationException e) {
            throw new RuntimeException("创建token出错");
        }
        return token;
    }

    public static Boolean verify(String token, String tokenSecret) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenSecret)).withIssuer("auth0").build();//创建token验证器
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
//        System.out.println("认证通过：");
//        System.out.println("username: " + decodedJWT.getClaim("username").asString());
//        System.out.println("过期时间：      " + decodedJWT.getExpiresAt());
        return true;
    }

    public static Long getTokenTime(String token){
        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("currentTime").asLong();
        }catch (JWTCreationException e){
            return null;
        }
    }

    public static String getUsername(String token){
        try{
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getClaim("username").asString();
        }catch (JWTCreationException e){
            return null;
        }
    }
}
