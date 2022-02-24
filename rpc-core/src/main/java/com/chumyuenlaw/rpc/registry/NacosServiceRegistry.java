package com.chumyuenlaw.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * <pre>
 * NacosServiceRegistry 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/21 15:51
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NacosServiceRegistry implements ServiceRegistry
{
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    private static final String SERVER_ADDR = "127.0.0.1:8848";
    private static final NamingService namingService;

    static
    {
        try
        {
            namingService = NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e)
        {
            logger.error("连接 Nacos 时有错误发生：" + e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress)
    {
        try
        {
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e)
        {
            logger.error("注册服务时有错误发生：" + e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

    @Override
    public InetSocketAddress lookupService(String serviceName)
    {
        try
        {
            List<Instance> instances = namingService.getAllInstances(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e)
        {
            logger.error("获取服务时有错误发生：" + e);
        }
        return null;
    }
}
