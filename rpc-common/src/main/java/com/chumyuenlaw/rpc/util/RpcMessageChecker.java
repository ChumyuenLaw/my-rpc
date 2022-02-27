package com.chumyuenlaw.rpc.util;

import com.chumyuenlaw.rpc.entity.RpcRequest;
import com.chumyuenlaw.rpc.entity.RpcResponse;
import com.chumyuenlaw.rpc.enumeration.ResponseCode;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * RpcMessageChecker 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/13 21:19
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class RpcMessageChecker
{
    private static final String INTERFACE_NAME = "interfaceName";

    private static final Logger logger = LoggerFactory.getLogger(RpcMessageChecker.class);

    private RpcMessageChecker()
    {
    }

    public static void check(RpcRequest rpcRequest, RpcResponse rpcResponse)
    {
        if (rpcResponse == null)
        {
            logger.error("调用服务失败，serviceName: {}", rpcRequest.getInterfaceName());
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ": " + rpcRequest.getInterfaceName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId()))
        {
            logger.error("相应与请求号不匹配");
            throw new RpcException(RpcError.RESPONSE_NOT_MATCH, INTERFACE_NAME + ": " + rpcRequest.getInterfaceName());
        }

        if (rpcResponse.getStatusCode() == null || !rpcResponse.getStatusCode().equals(ResponseCode.SUCCESS.getCode()))
        {
            logger.error("调用服务失败，serviceName: {}, RpcResponse: {}", rpcRequest.getInterfaceName(), rpcResponse);
            throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ": " + rpcRequest.getInterfaceName());
        }
    }
}
