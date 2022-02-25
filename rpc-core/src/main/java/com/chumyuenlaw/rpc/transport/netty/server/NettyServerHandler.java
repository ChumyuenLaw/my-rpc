package com.chumyuenlaw.rpc.transport.netty.server;

import com.chumyuenlaw.rpc.handler.RequestHandler;
import com.chumyuenlaw.rpc.entity.RpcRequest;
import com.chumyuenlaw.rpc.factory.ThreadPoolFactory;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * <pre>
 * NettyClientHandler 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/12 17:09
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest>
{
    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static final String THREAD_NAME_PREFIX = "netty-server-handler";
    private static final ExecutorService threadPool;

    static
    {
        requestHandler = new RequestHandler();
        //引入异步业务线程池，避免长时间的耗时业务阻塞netty本身的worker工作线程，耽误了同一个Selector中其他任务的执行
        threadPool = ThreadPoolFactory.createDefaultThreadPool(THREAD_NAME_PREFIX);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest msg) throws Exception
    {
        try
        {
            logger.info("服务端接收请求：{}", msg);

            Object response = requestHandler.handle(msg);

            ChannelFuture future = channelHandlerContext.writeAndFlush(response);

            future.addListener(ChannelFutureListener.CLOSE);
        } finally
        {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        logger.error("处理过程调用时有错误发生：");
        cause.printStackTrace();
        ctx.close();
    }
}
