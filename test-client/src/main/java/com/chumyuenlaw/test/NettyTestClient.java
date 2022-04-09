package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.ByeService;
import com.chumyuenlaw.rpc.loadbalancer.RandomLoadBalancer;
import com.chumyuenlaw.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcClient;
import com.chumyuenlaw.rpc.transport.RpcClientProxy;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.transport.netty.client.NettyClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
//        RpcClient client = new NettyClient(CommonSerializer.PROTOBUF_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClient client = new NettyClient(CommonSerializer.DEFAULT_SERIALIZER, new RandomLoadBalancer());
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        ByeService byeService = rpcClientProxy.getProxy(ByeService.class);
        HelloObject helloObject = new HelloObject(12, "Netty test msg.");
        String res = helloService.hello(helloObject);
        System.out.println(res);

//        final CountDownLatch begin = new CountDownLatch(1);
//        final CountDownLatch end = new CountDownLatch(4);
//        final ExecutorService executorService = Executors.newFixedThreadPool(4);
//        long[] result = new long[4];
//        for (int i = 0; i < 4; i++)
//        {
//            int finalI = i;
//            Runnable run = () ->
//            {
//                try
//                {
//                    begin.await();
//                    long startTime = System.currentTimeMillis();
//                    for (int i1 = 0; i1 < 1000; i1++)
//                    {
//                        String res = helloService.hello(helloObject);
//                        System.out.println(res);
//                    }
//                    //System.out.println(finalI + " 空指针次数：" + nullNum);
//                    long endTime = System.currentTimeMillis();
//                    result[finalI] = endTime - startTime;
//                    //System.out.println(endTime - startTime + " ms");
//                    end.countDown();
//                } catch (InterruptedException e)
//                {
//                    e.printStackTrace();
//                }
//            };
//            executorService.submit(run);
//        }
//        try
//        {
//            System.out.println("All threads ready.");
//            begin.countDown();
//            end.await();
//            for (int i = 0; i < 4; i++)
//            {
//                System.out.println(result[i] + " ms");
//            }
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
    }
}
