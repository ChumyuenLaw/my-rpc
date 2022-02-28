package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.ByeService;
import com.chumyuenlaw.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcClientProxy;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.transport.socket.client.SocketClient;

public class SocketTestClient
{
    public static void main(String[] args)
    {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        ByeService byeService = proxy.getProxy(ByeService.class);
        HelloObject helloObject = new HelloObject(12, "Socket Test message");

        for (int i = 0; i < 100; i++)
        {
            String res = helloService.hello(helloObject);
            System.out.println(res);
            res = byeService.bye("Socket");
            System.out.println(res);
        }

    }
}
