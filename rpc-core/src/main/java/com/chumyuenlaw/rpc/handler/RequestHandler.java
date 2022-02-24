package com.chumyuenlaw.rpc.handler;

import com.chumyuenlaw.rpc.entity.RpcRequest;
import com.chumyuenlaw.rpc.entity.RpcResponse;
import com.chumyuenlaw.rpc.enumeration.ResponseCode;
import com.chumyuenlaw.rpc.provider.ServiceProvider;
import com.chumyuenlaw.rpc.provider.ServiceProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RequestHandler
{
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private static final ServiceProvider serviceProvider;

    static
    {
        serviceProvider = new ServiceProviderImpl();
    }

    public Object handle(RpcRequest rpcRequest)
    {
        Object result = null;

        Object service = serviceProvider.getServiceProvider(rpcRequest.getInterfaceName());
        try
        {
            result = invokeTargetMethod(rpcRequest, service);
            logger.info("服务：{} 成功调用方法：{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        } catch (InvocationTargetException | IllegalAccessException e)
        {
            logger.error("调用或发送时有错误发生：", e);
        }
        return RpcResponse.success(result, rpcRequest.getRequestId());
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws InvocationTargetException, IllegalAccessException
    {
        Method method;
        try
        {
            method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
        } catch (NoSuchMethodException e)
        {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND, rpcRequest.getRequestId());
        }
        return method.invoke(service, rpcRequest.getParameters());
    }
}
