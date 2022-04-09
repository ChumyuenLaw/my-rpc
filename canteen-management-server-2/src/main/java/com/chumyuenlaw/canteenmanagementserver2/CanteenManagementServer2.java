package com.chumyuenlaw.canteenmanagementserver2;

import com.chumyuenlaw.rpc.annotation.ServiceScan;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcServer;
import com.chumyuenlaw.rpc.transport.netty.server.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.chumyuenlaw.canteenmanagementserver2")
@ServiceScan
public class CanteenManagementServer2
{

    public static void main(String[] args)
    {
        SpringApplication.run(CanteenManagementServer2.class, args);
        RpcServer canteenServer = new NettyServer("127.0.0.1", 9002, CommonSerializer.DEFAULT_SERIALIZER);
        canteenServer.start();
    }

}
