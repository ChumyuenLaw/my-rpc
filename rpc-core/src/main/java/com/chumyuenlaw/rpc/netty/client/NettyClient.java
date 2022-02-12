package com.chumyuenlaw.rpc.netty.client;

import com.chumyuenlaw.rpc.RpcClient;
import com.chumyuenlaw.rpc.codec.CommonDecoder;
import com.chumyuenlaw.rpc.codec.CommonEncoder;
import com.chumyuenlaw.rpc.entity.RpcRequest;
import com.chumyuenlaw.rpc.entity.RpcResponse;
import com.chumyuenlaw.rpc.serializer.JSONSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private String host;
    private int port;
    private static final Bootstrap bootstrap;

    public NettyClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    static
    {
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception
                    {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new CommonDecoder())
                                .addLast(new CommonEncoder(new JSONSerializer()))
                                .addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest)
    {
        try
        {
            ChannelFuture future = bootstrap.connect(host, port).sync();
            logger.info("连接到服务端：{}:{}", host, port);
            Channel channel = future.channel();
            if (channel != null)
            {
                channel.writeAndFlush(rpcRequest).addListener(future1 -> {
                    if (future1.isSuccess())
                        logger.info(String.format("客户端发送信息：%s", rpcRequest.toString()));
                    else
                        logger.error("发送信息时有错误发生：", future1.cause());
                });
                channel.closeFuture().sync();

                AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
                RpcResponse rpcResponse = channel.attr(key).get();
                return rpcResponse.getData();
            }
        } catch (InterruptedException e)
        {
            logger.error("发送信息时有错误发生：", e);
        }
        return null;
    }
}
