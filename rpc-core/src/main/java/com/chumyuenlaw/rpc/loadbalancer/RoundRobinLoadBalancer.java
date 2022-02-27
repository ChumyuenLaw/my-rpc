package com.chumyuenlaw.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * <pre>
 * RoundRobinLoadBalancer 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/26 22:11
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class RoundRobinLoadBalancer implements LoadBalancer
{
    private Integer index = 0;

    @Override
    public Instance select(List<Instance> instances)
    {
        synchronized (index)
        {
            if (index >= instances.size())
                index %= instances.size();
            return instances.get(index++);
        }
    }
}
