package com.chumyuenlaw.rpc.socket.server;

import com.chumyuenlaw.rpc.RequestHandler;
import com.chumyuenlaw.rpc.RpcServer;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import com.chumyuenlaw.rpc.registry.ServiceRegistry;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.util.ThreadPoolFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class SocketServer implements RpcServer
{
    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ExecutorService threadPool;
    private RequestHandler requestHandler = new RequestHandler();
    private final ServiceRegistry serviceRegistry;
    private CommonSerializer serializer;

    public SocketServer(ServiceRegistry serviceRegistry)
    {
        this.serviceRegistry = serviceRegistry;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-framework");
    }

    @Override
    public void start(int port)
    {
        if (serializer == null)
        {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            logger.info("服务器启动...");
            Socket socket;
            while ((socket = serverSocket.accept()) != null)
            {
                logger.info("Client 连接：{} : {}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
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
}
