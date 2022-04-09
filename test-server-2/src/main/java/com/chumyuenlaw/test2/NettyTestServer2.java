package com.chumyuenlaw.test2;

import com.chumyuenlaw.rpc.annotation.ServiceScan;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcServer;
import com.chumyuenlaw.rpc.transport.netty.server.NettyServer;

/**
 * <pre>
 * NettyTestServer2 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/2 22:31
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@ServiceScan
public class NettyTestServer2
{
    public static void main(String[] args)
    {
        RpcServer server = new NettyServer("127.0.0.1", 9003, CommonSerializer.DEFAULT_SERIALIZER);
        server.start();
    }
}
