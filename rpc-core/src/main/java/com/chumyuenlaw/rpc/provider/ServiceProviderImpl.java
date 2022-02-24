package com.chumyuenlaw.rpc.provider;

import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * ServiceProviderImpl 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/24 16:40
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ServiceProviderImpl implements ServiceProvider
{
    private static final Logger logger = LoggerFactory.getLogger(ServiceProviderImpl.class);

    private static final Map<String, Object> serviceMap = new ConcurrentHashMap<>();

    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();

    @Override
    public <T> void addServiceProvider(T service)
    {
        String serviceImplName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceImplName))
            return;
        registeredService.add(serviceImplName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(0 == interfaces.length)
            throw new RpcException(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE);
        for(Class<?> i : interfaces)
            serviceMap.put(i.getCanonicalName(), service);
        logger.info("向接口：{}，注册服务：{}", interfaces, serviceImplName);
    }

    @Override
    public Object getServiceProvider(String serviceName)
    {
        Object service = serviceMap.get(serviceName);
        if(service == null)
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        return service;
    }
}
