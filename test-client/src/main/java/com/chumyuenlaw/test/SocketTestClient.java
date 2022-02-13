package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.RpcClientProxy;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.serializer.HessianSerializer;
import com.chumyuenlaw.rpc.serializer.JSONSerializer;
import com.chumyuenlaw.rpc.serializer.KryoSerializer;
import com.chumyuenlaw.rpc.socket.client.SocketClient;

public class SocketTestClient
{
    public static void main(String[] args)
    {
        SocketClient client = new SocketClient("127.0.0.1", 9000);
        client.setSerializer(new KryoSerializer());
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloObject helloObject = new HelloObject(12, "Test message");
        HelloService helloService = proxy.getProxy(HelloService.class);
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}
