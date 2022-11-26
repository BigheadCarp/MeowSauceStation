package com.meowsauce.result;

import lombok.Data;
//前端响应数据
@Data
public class Result {
    private int code;
    private String message;
    private Object result;

    Result(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.result = data;
    }
}
