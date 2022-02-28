package com.chumyuenlaw.rpc.transport;

import com.chumyuenlaw.rpc.annotation.Service;
import com.chumyuenlaw.rpc.annotation.ServiceScan;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import com.chumyuenlaw.rpc.provider.ServiceProvider;
import com.chumyuenlaw.rpc.registry.ServiceRegistry;
import com.chumyuenlaw.rpc.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * <pre>
 * AbstractRpcServer 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/28 17:51
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public abstract class AbstractRpcServer implements RpcServer
{
    protected Logger logger = LoggerFactory.getLogger(AbstractRpcServer.class);

    protected String host;
    protected int port;

    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanServices()
    {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try
        {
            startClass = Class.forName(mainClassName);
            if (!startClass.isAnnotationPresent(ServiceScan.class))
            {
                logger.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e)
        {
            logger.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }

        String basePackage = startClass.getAnnotation(ServiceScan.class).value();
        if ("".equals(basePackage))
            basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));

        Set<Class<?>> classSet = ReflectUtil.getClasses(basePackage);
        for (Class<?> clazz : classSet)
        {
            if (clazz.isAnnotationPresent(Service.class))
            {
                String serviceName = clazz.getAnnotation(Service.class).name();
                Object obj;
                try
                {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e)
                {
                    logger.error("创造 " + clazz + " 时有错误发生");
                    continue;
                }
                if ("".equals(serviceName))
                {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface : interfaces)
                    {
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                }
                else
                    publishService(obj, serviceName);
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName)
    {
        serviceProvider.addServiceProvider(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
    }
}
