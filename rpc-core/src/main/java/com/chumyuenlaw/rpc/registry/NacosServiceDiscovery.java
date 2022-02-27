package com.chumyuenlaw.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.chumyuenlaw.rpc.loadbalancer.LoadBalancer;
import com.chumyuenlaw.rpc.loadbalancer.RandomLoadBalancer;
import com.chumyuenlaw.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * <pre>
 * NacosServiceDiscovery 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/25 15:34
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NacosServiceDiscovery implements ServiceDiscovery
{
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);

    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer)
    {
        if (loadBalancer == null)
            this.loadBalancer = new RandomLoadBalancer();
        else
            this.loadBalancer = loadBalancer;
    }

    @Override
    public InetSocketAddress lookupService(String serviceName)
    {
        try
        {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e)
        {
            logger.error("获取服务时有错误发生：", e);
        }
        return null;
    }
}
