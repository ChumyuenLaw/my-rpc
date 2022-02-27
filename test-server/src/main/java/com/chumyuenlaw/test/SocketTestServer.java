package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.serializer.KryoSerializer;
import com.chumyuenlaw.rpc.transport.socket.server.SocketServer;

public class SocketTestServer
{
    public static void main(String[] args)
    {
        HelloService helloService = new HelloServiceImpl2();
        SocketServer socketServer = new SocketServer("127.0.0.1", 9001, CommonSerializer.HESSIAN_SERIALIZER);
        socketServer.publishService(helloService, HelloService.class);
    }
}
