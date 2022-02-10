package com.chumyuenlaw.rpc;

import com.chumyuenlaw.rpc.entity.RpcRequest;

/**
 * <pre>
 * RpcClient 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/10 19:45
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface RpcClient
{
    Object sendRequest(RpcRequest rpcRequest);
}
