package com.wei.myblog.common;

public class Result {

    private Integer code;
    private Boolean success;
    private String msg;
    private Object data;

    public static Result succeed(Integer code, Boolean success, String msg, Object data){
        return new Result(code, success, msg, data);
    }

    public static Result succeed(String msg, Object data){
        return new Result(200, true, msg, data);
    }

    public static Result succeed(Object data){
        return new Result(200, true, "请求成功", data);
    }

    public static Result succeed(){
        return new Result(200, true, "请求成功", "Empty");
    }

    public static Result fail(Integer code, Boolean success, String msg, Object data){
        return new Result(code, success, msg, data);
    }

    public static Result fail(Integer code, String msg){
        return new Result(code, false, msg, "Empty");
    }

    public static Result fail(Integer code){
        return new Result(code, false, "请求失败", "Empty");
    }

    public static Result fail(String msg){
        return new Result(400, false, msg, "Empty");
    }

    public Result(Integer code, Boolean success, String msg, Object data) {
        this.code = code;
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
