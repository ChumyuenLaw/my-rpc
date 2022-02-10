package com.chumyuenlaw.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCode
{
    SUCCESS(200, "调用成功"),
    FAIL(500, "调用失败"),
    METHOD_NOT_FOUND(500,"未找到指定方法"),
    ClASS_NOT_FOUND(500,"未找到指定类");

    private final int code;

    private final String message;
}
