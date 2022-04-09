package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.AddService;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.loadbalancer.RandomLoadBalancer;
import com.chumyuenlaw.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcClient;
import com.chumyuenlaw.rpc.transport.RpcClientProxy;
import com.chumyuenlaw.rpc.transport.netty.client.NettyClient;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * NettyTestClient2 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/2 23:53
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NettyTestClient2
{
    public static void main(String[] args)
    {
        RpcClient client = new NettyClient(CommonSerializer.KRYO_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        AddService addService = proxy.getProxy(AddService.class);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++)
        {
            Random random = new Random();
            int res = addService.add(random.nextInt(), random.nextInt());
            System.out.println(res);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + " ms");

//        final int threadsNum = 2;
//        final int loopCount = 1000;
//        final CountDownLatch begin = new CountDownLatch(1);
//        final CountDownLatch end = new CountDownLatch(threadsNum);
//        final ExecutorService executorService = Executors.newFixedThreadPool(threadsNum);
//        long[] result = new long[threadsNum];
//        for (int i = 0; i < threadsNum; i++)
//        {
//            int finalI = i;
//            Runnable run = () ->
//            {
//                try
//                {
//                    begin.await();
//                    long startTime = System.currentTimeMillis();
//                    for (int i1 = 0; i1 < loopCount; i1++)
//                    {
//                        Random random = new Random(System.currentTimeMillis());
//                        int res = addService.add(random.nextInt(100), random.nextInt(100));
//                    }
//                    long endTime = System.currentTimeMillis();
//                    result[finalI] = endTime - startTime;
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
//            for (int i = 0; i < threadsNum; i++)
//            {
//                System.out.println(result[i] + " ms");
//            }
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
    }
}
