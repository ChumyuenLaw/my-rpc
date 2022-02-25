package com.chumyuenlaw.rpc.hook;

import com.chumyuenlaw.rpc.factory.ThreadPoolFactory;
import com.chumyuenlaw.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * <pre>
 * ShutdownHook 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/25 16:00
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ShutdownHook
{
    private static final Logger logger = LoggerFactory.getLogger(ShutdownHook.class);

    private static final ShutdownHook shutdownHook = new ShutdownHook();

    private final ExecutorService threadPool = ThreadPoolFactory.createDefaultThreadPool("shutdown-hook");

    public static ShutdownHook getShutdownHook()
    {
        return shutdownHook;
    }

    public void addClearAllHook()
    {
        logger.info("服务端关闭前将自动注销所有服务");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            NacosUtil.clearRegistry();
            threadPool.shutdown();
        }));
    }
}
