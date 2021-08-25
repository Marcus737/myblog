package com.wei.myblog.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wei.myblog.common.JWTToken;
import com.wei.myblog.common.Result;
import com.wei.myblog.utils.TokenUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * jwt安全过滤器
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    /**
     * 拦截器的前置方法，此处进行跨域处理
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest= (HttpServletRequest) request;
        HttpServletResponse httpServletResponse= (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods","GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","*");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "refreshtoken");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "expiretoken");


        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())){
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }

        if (!isLoginAttempt(request,response)){
            responseError(httpServletResponse,"no token");
            return false;
        }
        return super.preHandle(request,response);
    }

    /**
     * 判断是否登陆了
     * true就调用onLoginSuccess
     * false调用onAccessDenied
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            return executeLogin(request,response);
        } catch (Exception e) {
            responseError(response, "验证登录状态失败");
            return false;
        }

    }

    /**
     * 是否尝试登录登录
     * 如果请求头有token就尝试登录
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String refreshToken = ((HttpServletRequest)request).getHeader("RefreshToken");
        String expireToken = ((HttpServletRequest)request).getHeader("ExpireToken");
        if (refreshToken != null && expireToken != null){
            return true;
        }
        return false;
    }

    /**
     * 在这之前会先调用isLoginAttempt尝试登录
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        this.sendChallenge(request,response);
        responseError(response,"token验证失败");
        return false;
    }

    /**
     * 得到shiro token
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String expireToken = ((HttpServletRequest)request).getHeader("ExpireToken");
        if(expireToken != null) {
            return new JWTToken(expireToken);
        }
        return null;
    }

    /**
     * shiro验证成功调用
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        String refreshToken = (String) token.getPrincipal();
        String expireToken = ((HttpServletRequest)request).getHeader("ExpireToken");
        try{
            TokenUtils.verify(refreshToken);
            TokenUtils.verify(expireToken);
        }catch (Exception e){
            if (e instanceof TokenExpiredException){
                return refreshToken(request, response);
            }
        }
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("RefreshToken", refreshToken);
        httpServletResponse.setHeader("ExpireToken", expireToken);
        return true;
    }

    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        String refreshToken = ((HttpServletRequest)request).getHeader("RefreshToken");
        String expireToken = ((HttpServletRequest)request).getHeader("ExpireToken");
        long curTime = System.currentTimeMillis();
        Long expireTime = TokenUtils.getTokenTime(expireToken);
        String username = TokenUtils.getUsername(refreshToken);
        if (curTime >= expireTime){
            return false;
        }
        /**
         * 更新refreshToken
         */
        String newRefreshToken = TokenUtils.getToken(username, TokenUtils.REFRESH_EXPIRE_TIME);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("RefreshToken", newRefreshToken);
        httpServletResponse.setHeader("ExpireToken", expireToken);
        return true;
    }

    private void responseError(ServletResponse response,String msg){
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(401);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json;charset=UTF-8");
        try {
            String info = new ObjectMapper().writeValueAsString(Result.fail(401, false, msg, null));

            httpResponse.getWriter().append(info);
        } catch (IOException e) {}
    }
}
