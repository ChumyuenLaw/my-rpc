package com.chumyuenlaw.rpc.transport.socket.server;

import com.chumyuenlaw.rpc.handler.RequestHandler;
import com.chumyuenlaw.rpc.hook.ShutdownHook;
import com.chumyuenlaw.rpc.provider.ServiceProviderImpl;
import com.chumyuenlaw.rpc.registry.NacosServiceRegistry;
import com.chumyuenlaw.rpc.transport.AbstractRpcServer;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.factory.ThreadPoolFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketServer extends AbstractRpcServer
{
    private final ExecutorService threadPool;
    private final RequestHandler requestHandler = new RequestHandler();
    private final CommonSerializer serializer;

    public SocketServer(String host, int port)
    {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketServer(String host, int port, Integer serializerCode)
    {
        this.host = host;
        this.port = port;
        serviceProvider = new ServiceProviderImpl();
        serviceRegistry = new NacosServiceRegistry();
        serializer = CommonSerializer.getByCode(serializerCode);
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        scanServices();
    }

    @Override
    public void start()
    {
        try(ServerSocket serverSocket = new ServerSocket())
        {
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动...");
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            while ((socket = serverSocket.accept()) != null)
            {
                logger.info("Client 连接：{} : {}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e)
        {
            logger.error("服务器启动出错：", e);
        }
    }
}
