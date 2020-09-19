package com.wei.myblog.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpsUtils {

    private static final int TIMEOUT = 4000;

    /**
     * 向指定URL发送GET方法的请求
     */
    public static String sendGet(String url, Map<String, String> header) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader in;
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection connection = realUrl.openConnection();
        //设置超时时间
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        // 设置通用的请求属性
//        if (header != null) {
//            for (Map.Entry<String, String> entry : header.entrySet()) {
//                connection.setRequestProperty(entry.getKey(), entry.getValue());
//            }
//        }
        // 建立实际的连接
        connection.connect();
        // 获取所有响应头字段
        connection.getHeaderFields();
        // 定义 BufferedReader输入流来读取URL的响应，设置utf8防止中文乱码
        in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        in.close();
        return result.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     */
    public static String sendPost(String url, String param, Map<String, String> header) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();
        //设置超时时间
        conn.setConnectTimeout(TIMEOUT);
        conn.setReadTimeout(TIMEOUT);
        // 设置通用的请求属性
        if (header != null) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        // 发送POST请求必须设置如下两行
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // 获取URLConnection对象对应的输出流
        out = new PrintWriter(conn.getOutputStream());
        // 发送请求参数
        out.print(param);
        // flush输出流的缓冲
        out.flush();
        // 定义BufferedReader输入流来读取URL的响应
        in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf8"));
        String line;
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        return result.toString();
    }
}
