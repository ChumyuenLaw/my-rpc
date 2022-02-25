package com.chumyuenlaw.rpc.transport.socket.client;

import com.chumyuenlaw.rpc.registry.NacosServiceDiscovery;
import com.chumyuenlaw.rpc.registry.NacosServiceRegistry;
import com.chumyuenlaw.rpc.registry.ServiceDiscovery;
import com.chumyuenlaw.rpc.registry.ServiceRegistry;
import com.chumyuenlaw.rpc.transport.RpcClient;
import com.chumyuenlaw.rpc.entity.RpcRequest;
import com.chumyuenlaw.rpc.entity.RpcResponse;
import com.chumyuenlaw.rpc.enumeration.ResponseCode;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.socket.util.ObjectReader;
import com.chumyuenlaw.rpc.transport.socket.util.ObjectWriter;
import com.chumyuenlaw.rpc.util.RpcMessageChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient implements RpcClient
{
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final ServiceDiscovery serviceDiscovery;

    private CommonSerializer serializer;

    public SocketClient()
    {
        serviceDiscovery = new NacosServiceDiscovery();
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest)
    {
        if (serializer == null)
        {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());

        try(Socket socket = new Socket())
        {
            socket.connect(inetSocketAddress);
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse)obj;
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse.getData();
        } catch (IOException e)
        {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务端调用失败：", e);
        }
    }

    @Override
    public void setSerializer(CommonSerializer serializer)
    {
        this.serializer = serializer;
    }
}
