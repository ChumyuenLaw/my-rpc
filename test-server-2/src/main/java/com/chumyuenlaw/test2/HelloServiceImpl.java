package com.chumyuenlaw.test2;

import com.chumyuenlaw.rpc.annotation.Service;
import com.chumyuenlaw.rpc.api.HelloObject;
import com.chumyuenlaw.rpc.api.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * HelloServiceImpl 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/2 22:34
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
public class HelloServiceImpl implements HelloService
{
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject helloObject)
    {
        logger.info("Server 2 接收到：{}", helloObject.getMsg());
        return "Server 2 成功调用 hello() 方法";
    }
}
