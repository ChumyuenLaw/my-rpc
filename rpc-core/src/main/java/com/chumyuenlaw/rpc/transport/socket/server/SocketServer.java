package com.chumyuenlaw.rpc.transport.socket.server;

import com.chumyuenlaw.rpc.handler.RequestHandler;
import com.chumyuenlaw.rpc.hook.ShutdownHook;
import com.chumyuenlaw.rpc.provider.ServiceProvider;
import com.chumyuenlaw.rpc.provider.ServiceProviderImpl;
import com.chumyuenlaw.rpc.registry.NacosServiceRegistry;
import com.chumyuenlaw.rpc.transport.RpcServer;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import com.chumyuenlaw.rpc.registry.ServiceRegistry;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.factory.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketServer implements RpcServer
{
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private CommonSerializer serializer;

    private final String host;
    private final int port;

    private final ServiceProvider serviceProvider;
    private final ServiceRegistry serviceRegistry;

    public SocketServer(String host, int port)
    {
        this.host = host;
        this.port = port;
        serviceProvider = new ServiceProviderImpl();
        serviceRegistry = new NacosServiceRegistry();
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-framework");
    }

    @Override
    public void start()
    {
        if (serializer == null)
        {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            logger.info("服务器启动...");
            ShutdownHook.getShutdownHook().addClearAllHook();
            Socket socket;
            while ((socket = serverSocket.accept()) != null)
            {
                logger.info("Client 连接：{} : {}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e)
        {
            logger.error("服务器启动出错：", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer)
    {
        this.serializer = serializer;
    }

    @Override
    public <T> void publishService(Object service, Class<T> serviceClass)
    {
        if (serializer == null)
        {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        serviceProvider.addServiceProvider(service);
        serviceRegistry.register(serviceClass.getCanonicalName(), new InetSocketAddress(host, port));
        start();
    }
}
