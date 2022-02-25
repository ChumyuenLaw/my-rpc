package com.chumyuenlaw.rpc.util;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * NacosUtil 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/25 15:05
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NacosUtil
{
    private static final Logger logger = LoggerFactory.getLogger(NacosUtil.class);

    private static final String SERVER_ADDR = "127.0.0.1:8848";

    private static final NamingService namingService;
    private static final Set<String> serviceNames = new HashSet<>();
    private static InetSocketAddress address;

    static
    {
        namingService = getNacosNamingService();
    }

    public static NamingService getNacosNamingService()
    {
        try
        {
            return NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e)
        {
            logger.error("连接 Nacos 时有错误发生：", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    public static void registerService(String serviceName, InetSocketAddress inetSocketAddress) throws NacosException
    {
        namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        NacosUtil.address = inetSocketAddress;
        serviceNames.add(serviceName);
    }

    public static List<Instance> getAllInstance(String serviceName) throws NacosException
    {
        return namingService.getAllInstances(serviceName);
    }

    public static void clearRegistry()
    {
        if (!serviceNames.isEmpty() && address != null)
        {
            String host = address.getHostName();
            int port = address.getPort();

            Iterator<String> iterator = serviceNames.iterator();
            while (iterator.hasNext())
            {
                String serviceName = iterator.next();
                try
                {
                    namingService.deregisterInstance(serviceName, host, port);
                } catch (NacosException e)
                {
                    logger.error("注销服务 {} 失败", serviceName, e);
                }
            }
        }
    }
}
