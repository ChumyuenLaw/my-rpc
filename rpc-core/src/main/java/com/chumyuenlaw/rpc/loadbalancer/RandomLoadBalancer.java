package com.chumyuenlaw.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;
import java.util.Random;

/**
 * <pre>
 * RandomLoadBalancer 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/26 22:09
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class RandomLoadBalancer implements LoadBalancer
{
    @Override
    public Instance select(List<Instance> instances)
    {
        return instances.get(new Random().nextInt(instances.size()));
    }
}
