package com.chumyuenlaw.rpc.transport.netty.client;

import com.chumyuenlaw.rpc.codec.CommonDecoder;
import com.chumyuenlaw.rpc.codec.CommonEncoder;
import com.chumyuenlaw.rpc.enumeration.RpcError;
import com.chumyuenlaw.rpc.exception.RpcException;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * ChannelProvider 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/14 17:28
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ChannelProvider
{
    private static final Logger logger = LoggerFactory.getLogger(ChannelProvider.class);
    private static EventLoopGroup eventLoopGroup;
    private static Bootstrap bootstrap = initializeBootstrap();

    private static final int MAX_RETRY_COUNT = 5;
    private static Channel channel = null;

    private static Bootstrap initializeBootstrap()
    {
        eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true);
        return bootstrap;
    }

    public static Channel get(InetSocketAddress inetSocketAddress, CommonSerializer serializer)
    {
        bootstrap.handler(new ChannelInitializer<SocketChannel>()
        {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception
            {
                socketChannel.pipeline()
                        .addLast(new CommonEncoder(serializer))
                        .addLast(new CommonDecoder())
                        .addLast(new NettyClientHandler());
            }
        });
        CountDownLatch countDownLatch = new CountDownLatch(1);
        try
        {
            connect(bootstrap, inetSocketAddress, countDownLatch);
            countDownLatch.await();
        } catch (InterruptedException e)
        {
            logger.error("获取 Channel 时有错误发生：", e);
        }
        return channel;
    }

    private static void connect(Bootstrap bootstrap, InetSocketAddress inetSocketAddress, CountDownLatch countDownLatch)
    {
        connect(bootstrap, inetSocketAddress, MAX_RETRY_COUNT, countDownLatch);
    }

    private static void connect(Bootstrap bootstrap, InetSocketAddress inetSocketAddress, int retry, CountDownLatch countDownLatch)
    {
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess())
            {
                logger.info("客户端连接成功！");
                channel = future.channel();
                countDownLatch.countDown();
                return;
            }
            if (retry == 0)
            {
                logger.error("客户端连接失败：重试次数用完，放弃连接！");
                countDownLatch.countDown();
                throw new RpcException(RpcError.CLIENT_CONNECT_SERVER_FAILURE);
            }
            int order = (MAX_RETRY_COUNT - retry) + 1; // 第几次重连
            int delay = 1 << order; // 重连时间间隔，1 * 2 ^ order
            logger.error("{}：连接失败，第{}次重连...", new Date(), order);

            bootstrap.config().group().schedule(() -> connect(bootstrap, inetSocketAddress, retry - 1, countDownLatch), delay, TimeUnit.SECONDS);
        });
    }
}
