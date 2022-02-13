package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.RpcClient;
import com.chumyuenlaw.rpc.RpcClientProxy;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import com.chumyuenlaw.rpc.netty.client.NettyClient;

/**
 * <pre>
 * NettyTestClient 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/12 17:26
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class NettyTestClient
{
    public static void main(String[] args)
    {
        RpcClient client = new NettyClient("127.0.0.1", 9001);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(client);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject helloObject = new HelloObject(12, "Netty test msg.");
        String res = helloService.hello(helloObject);
        System.out.println(res);
    }
}