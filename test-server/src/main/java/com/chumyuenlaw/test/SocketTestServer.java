package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.annotation.ServiceScan;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.socket.server.SocketServer;

@ServiceScan
public class SocketTestServer
{
    public static void main(String[] args)
    {
        SocketServer socketServer = new SocketServer("127.0.0.1", 9001, CommonSerializer.HESSIAN_SERIALIZER);
        socketServer.start();
    }
}
