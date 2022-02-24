package com.chumyuenlaw.rpc.transport.netty.client;

import com.chumyuenlaw.rpc.registry.NacosServiceRegistry;
import com.chumyuenlaw.rpc.registry.ServiceRegistry;
import com.chumyuenlaw.rpc.transport.RpcClient;
import com.chumyuenlaw.rpc.entity.RpcRequest;
import com.chumyuenlaw.rpc.entity.RpcResponse;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.util.RpcMessageChecker;
import io.netty.channel.*;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * NettyClient 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/11 14:58
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NettyClient implements RpcClient
{
    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private final ServiceRegistry serviceRegistry;

    private CommonSerializer serializer;

    public NettyClient()
    {
        serviceRegistry = new NacosServiceRegistry();
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest)
    {
        if (serializer == null)
        {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }

        AtomicReference<Object> result = new AtomicReference<>(null);
        try
        {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            Channel channel = ChannelProvider.get(inetSocketAddress, serializer);
            if (channel.isActive())
            {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess())
                        logger.info(String.format("客户端发送信息：%s", rpcRequest.toString()));
                    else
                        logger.error("发送信息时有错误发生：", future1.cause());
                });
                channel.closeFuture().sync();

                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse" + rpcRequest.getRequestId());
                RpcResponse rpcResponse = channel.attr(key).get();
                RpcMessageChecker.check(rpcRequest, rpcResponse);
                result.set(rpcResponse.getData());
            }
            else
            {
                System.exit(0);
            }
        } catch (InterruptedException e)
        {
            logger.error("发送信息时有错误发生：", e);
        }
        return result.get();
    }

    @Override
    public void setSerializer(CommonSerializer serializer)
    {
        this.serializer = serializer;
    }
}
