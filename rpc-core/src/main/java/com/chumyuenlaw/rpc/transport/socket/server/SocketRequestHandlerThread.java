package com.chumyuenlaw.rpc.transport.socket.server;

import com.chumyuenlaw.rpc.handler.RequestHandler;
import com.chumyuenlaw.rpc.entity.RpcRequest;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.socket.util.ObjectReader;
import com.chumyuenlaw.rpc.transport.socket.util.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class SocketRequestHandlerThread implements Runnable
{
    private static final Logger logger = LoggerFactory.getLogger(SocketRequestHandlerThread.class);

    private Socket socket;
    private RequestHandler requestHandler;
    private CommonSerializer serializer;

    public SocketRequestHandlerThread(Socket socket, RequestHandler requestHandler, CommonSerializer serializer)
    {
        this.socket = socket;
        this.requestHandler = requestHandler;
        this.serializer = serializer;
    }

    @Override
    public void run()
    {
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream())
        {
            RpcRequest rpcRequest = (RpcRequest) ObjectReader.readObject(inputStream);
            Object response = requestHandler.handle(rpcRequest);
            ObjectWriter.writeObject(outputStream, response, serializer);
        } catch (IOException e)
        {
            logger.error("调用或发送时有错误发生：", e);
        }
    }
}
