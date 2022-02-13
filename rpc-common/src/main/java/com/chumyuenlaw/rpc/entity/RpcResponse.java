package com.chumyuenlaw.rpc.entity;

import com.chumyuenlaw.rpc.enumeration.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class RpcResponse<T> implements Serializable
{
    private String requestId;

    private Integer statusCode;

    private String message;

    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId)
    {
        RpcResponse<T> rpcResponse = new RpcResponse<T>();
        rpcResponse.setRequestId(requestId);
        rpcResponse.setData(data);
        rpcResponse.setStatusCode(ResponseCode.SUCCESS.getCode());
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId)
    {
        RpcResponse<T> rpcResponse = new RpcResponse<T>();
        rpcResponse.setRequestId(requestId);
        rpcResponse.setStatusCode(code.getCode());
        rpcResponse.setMessage(code.getMessage());
        return rpcResponse;
    }
}
