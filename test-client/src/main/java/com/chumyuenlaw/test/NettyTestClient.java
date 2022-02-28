package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.ByeService;
import com.chumyuenlaw.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcClient;
import com.chumyuenlaw.rpc.transport.RpcClientProxy;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.transport.netty.client.NettyClient;

/**
 * <pre>
 * NettyTestClient 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/12 17:26
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NettyTestClient
{
    public static void main(String[] args)
    {
        // netty client 使用随机负载均衡可能会导致线程安全问题，建议使用轮询负载均衡：new RoundRobinLoadBalancer()
        RpcClient client = new NettyClient(CommonSerializer.PROTOBUF_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        HelloObject helloObject = new HelloObject(12, "Netty test msg.");
        //for (int i = 0; i < 20; i++)
        {
            String res = helloService.hello(helloObject);
            System.out.println(res);
            res = byeService.bye("Netty");
            System.out.println(res);
        }

    }
}
