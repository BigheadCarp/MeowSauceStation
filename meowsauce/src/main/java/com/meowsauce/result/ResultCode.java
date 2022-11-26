package com.meowsauce.result;

public enum ResultCode {
    SUCCESS(200),
    FALL(400),
    UNAUTHORIZED(401),//未授权的
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);//内部服务器错误

    public int code;

    ResultCode(int code){
        this.code = code;
    }
}
