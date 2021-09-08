package com.chumyuenlaw.rpc.entity;

import com.chumyuenlaw.rpc.enumeration.ResponseCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse<T> implements Serializable
{
    private Integer statusCode;

    private String message;

    private T data;

    public static <T> RpcResponse<T> success(T data)
    {
        RpcResponse<T> rpcResponse = new RpcResponse<T>();
        rpcResponse.setData(data);
        rpcResponse.setStatusCode(ResponseCode.SUCCESS.getCode());
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail(T data)
    {
        RpcResponse<T> rpcResponse = new RpcResponse<T>();
        rpcResponse.setData(data);
        rpcResponse.setStatusCode(ResponseCode.FAIL.getCode());
        return rpcResponse;
    }
}
