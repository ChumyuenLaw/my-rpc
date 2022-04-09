package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.annotation.ServiceScan;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.netty.server.NettyServer;


/**
 * <pre>
 * NettyTestServer 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/12 17:30
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@ServiceScan
public class NettyTestServer
{
    public static void main(String[] args)
    {
        NettyServer server = new NettyServer("127.0.0.1", 9002, CommonSerializer.PROTOBUF_SERIALIZER);
        server.start();
        // nacos dir: D:\nacos-server-1.3.0\nacos\bin\startup.cmd

    }
}
