package com.chumyuenlaw.rpc.transport.netty.client;

import com.chumyuenlaw.rpc.entity.RpcResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * UnprocessedRequest 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/26 16:38
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class UnprocessedRequest
{
    private static ConcurrentHashMap<String, CompletableFuture<RpcResponse>> unprocessedRequests = new ConcurrentHashMap<>();

    public void put(String requsetId, CompletableFuture<RpcResponse> future)
    {
        unprocessedRequests.put(requsetId, future);
    }

    public void remove(String requestId)
    {
        unprocessedRequests.remove(requestId);
    }

    public void complete(RpcResponse rpcResponse)
    {
        CompletableFuture<RpcResponse> future = unprocessedRequests.remove(rpcResponse.getRequestId());
        if (future != null)
            future.complete(rpcResponse);
        else
            throw new IllegalStateException();
    }
}
