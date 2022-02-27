package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcClientProxy;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.serializer.KryoSerializer;
import com.chumyuenlaw.rpc.transport.socket.client.SocketClient;

public class SocketTestClient
{
    public static void main(String[] args)
    {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloObject helloObject = new HelloObject(12, "Socket Test message");
        HelloService helloService = proxy.getProxy(HelloService.class);
        for (int i = 0; i < 100; i++)
        {
            String res = helloService.hello(helloObject);
            System.out.println(res);
        }

    }
}
