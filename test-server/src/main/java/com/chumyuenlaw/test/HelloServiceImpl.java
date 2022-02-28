package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.annotation.Service;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class HelloServiceImpl implements HelloService
{
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject helloObject)
    {
        logger.info("接收到：{}", helloObject.getMsg());
        return "成功调用 hello() 方法";
    }
}
