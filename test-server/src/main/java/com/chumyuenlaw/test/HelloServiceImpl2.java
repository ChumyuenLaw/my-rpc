package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * HelloServiceImpl2 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/25 17:47
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class HelloServiceImpl2 implements HelloService
{
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl2.class);

    @Override
    public String hello(HelloObject helloObject)
    {
        logger.info("接收到：{}", helloObject.getMsg());
        return "来自 Socket 服务：这是调用的返回值，id = " + helloObject.getId();
    }
}
