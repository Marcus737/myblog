package com.wei.myblog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtils {
    /**
     * 验证邮箱格式
     * @param email 邮箱字符串
     */
    public static boolean verifyEmail(String email){
        String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
}
