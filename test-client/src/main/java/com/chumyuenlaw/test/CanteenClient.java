package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.AddService;
import com.chumyuenlaw.rpc.api.CanteenService;
import com.chumyuenlaw.rpc.api.Dish;
import com.chumyuenlaw.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcClient;
import com.chumyuenlaw.rpc.transport.RpcClientProxy;
import com.chumyuenlaw.rpc.transport.netty.client.NettyClient;

import java.util.List;

/**
 * <pre>
 * CanteenClient 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/8 21:59
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class CanteenClient
{
    public static void main(String[] args)
    {
        RpcClient canteenClient = new NettyClient(CommonSerializer.DEFAULT_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(canteenClient);
        CanteenService canteenService = proxy.getProxy(CanteenService.class);
        List<Dish> list = canteenService.findDishesList();
        System.out.println(list.get(0).getDishName());
//        AddService addService = proxy.getProxy(AddService.class);
//        System.out.println(addService.add(1, 1));
    }
}
