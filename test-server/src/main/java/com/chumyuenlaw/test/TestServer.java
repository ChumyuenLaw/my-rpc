package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.transport.server.RpcServer;

public class TestServer
{
    public static void main(String[] args)
    {
        HelloService helloService = new HelloServiceImpl();
        RpcServer rpcServer = new RpcServer();
        rpcServer.register(helloService, 9000);
    }
}
