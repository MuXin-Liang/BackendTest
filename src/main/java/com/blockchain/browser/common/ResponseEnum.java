package com.blockchain.browser.common;

public enum ResponseEnum {
    SUCCESS(1000,"成功"),
    FAILED(1001,"失败"),

    INTERNAL_ERROR(2001, "内部错误"),

    GET_SUCCESS(20000, "数据获取成功"),
    SAVE_SUCCESS(20001, "数据上传成功"),
    SAVE_FAILED(20002, "数据上传失败"),
    DATA_NOT_EXIST(20003, "数据不存在"),
    DATA_NOT_MATCH(20004,"数据不匹配"),
    GET_FAILED(20005, "数据获取失败"),
    ABI_NOT_EXIST(20006, "未找到指定ABI"),
    ;

    Integer code;		//响应码
    String message;		//响应信息

    ResponseEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}