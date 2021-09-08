package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceImpl implements HelloService
{
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    public String hello(HelloObject helloObject)
    {
        logger.info("接收到：{}", helloObject.getMsg());
        return "这是调用的返回值，id = " + helloObject.getId();
    }
}
