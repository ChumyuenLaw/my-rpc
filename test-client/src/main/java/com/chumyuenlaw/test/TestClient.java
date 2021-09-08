package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.transport.client.RpcClientProxy;

public class TestClient
{
    public static void main(String[] args)
    {
        RpcClientProxy proxy = new RpcClientProxy("127.0.0.1", 9000);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(12, "Hello, World!");
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}
