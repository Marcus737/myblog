//
//package com.wei.myblog.handle;
//
//import com.wei.myblog.common.Result;
//import org.apache.shiro.ShiroException;
//import org.apache.shiro.authz.UnauthenticatedException;
//import org.apache.shiro.authz.UnauthorizedException;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
///**
// * @author panxuwei
// */
//@ControllerAdvice
//@ResponseBody
//public class GlobalExceptionHandler {
//
//    /**
//     * 捕捉所有Shiro异常
//     */
//    @ExceptionHandler(ShiroException.class)
//    public Result handle401(ShiroException e) {
//        return Result.fail(401, false, "无权访问(Unauthorized):", null);
//    }
//
//    /**
//     * 单独捕捉Shiro(UnauthorizedException)异常 该异常为访问有权限管控的请求而该用户没有所需权限所抛出的异常
//     */
//    @ExceptionHandler(UnauthorizedException.class)
//    public Result handle401(UnauthorizedException e) {
//        return Result.fail(401, false, "无权访问(Unauthorized):当前Subject没有此请求所需权限", null);
//    }
//
//    /**
//     * 单独捕捉Shiro(UnauthenticatedException)异常
//     * 该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
//     */
//    @ExceptionHandler(UnauthenticatedException.class)
//    public Result handle401(UnauthenticatedException e) {
//        return Result.fail(401, false, "无权访问(Unauthorized):当前Subject是匿名Subject，请先登录(This subject is anonymous.)", null);
//    }
//
//
//    /**
//     * 捕捉404异常
//     */
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public Result handle(NoHandlerFoundException e) {
//        return Result.fail(404, false, "请求路径不存在找不到", null);
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    public Result handleIllegalArgument(IllegalArgumentException e){
//        return Result.fail(400, false, e.getMessage(), null);
//    }
//
//    /**
//     * 捕捉其他所有异常
//     */
//    @ExceptionHandler(Exception.class)
//    public Result globalException(Exception e) {
//        return Result.fail(500, false, "服务器开小差了，稍后再试", null);
//    }
//
//}
//
