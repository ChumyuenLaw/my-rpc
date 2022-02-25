package com.chumyuenlaw.rpc.factory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

/**
 * <pre>
 * ThreadPoolFactory 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/13 16:10
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@NoArgsConstructor
public class ThreadPoolFactory
{
    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolFactory.class);

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAXIMUM_POOL_SIZE = 100;
    private static final int KEEP_ALIVE_TIME = 1;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;

    private static Map<String, ExecutorService> threadPoolMap = new ConcurrentHashMap<>();

    public static ExecutorService createDefaultThreadPool(String threadNamePrefix)
    {
        return createDefaultThreadPool(threadNamePrefix, false);
    }

    public static ExecutorService createDefaultThreadPool(String threadNamePrefix, Boolean daemon)
    {
        ExecutorService pool = threadPoolMap.computeIfAbsent(threadNamePrefix, k -> createTreadPool(threadNamePrefix, daemon));

        if (pool.isShutdown() || pool.isTerminated())
        {
            threadPoolMap.remove(threadNamePrefix);

            pool = createTreadPool(threadNamePrefix, daemon);
            threadPoolMap.put(threadNamePrefix, pool);
        }
        return pool;
    }

    public static void shutdownAll()
    {
        logger.info("关闭所有线程池...");

        threadPoolMap.entrySet().parallelStream().forEach(entry -> {
            ExecutorService executorService = entry.getValue();
            executorService.shutdown();
            logger.info("关闭线程池 [{}] [{}]", entry.getKey(), executorService.isTerminated());
            try
            {
                executorService.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e)
            {
                logger.error("关闭线程池失败");
                executorService.shutdownNow();
            }
        });
    }

    private static ExecutorService createTreadPool(String threadNamePrefix, Boolean daemon)
    {
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = createThreadFactory(threadNamePrefix, daemon);
        return new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MINUTES, workQueue, threadFactory);
    }

    private static ThreadFactory createThreadFactory(String threadNamePrefix, Boolean daemon)
    {
        if (threadNamePrefix != null)
        {
            if (daemon != null)
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").setDaemon(daemon).build();
            else
                return new ThreadFactoryBuilder().setNameFormat(threadNamePrefix + "-%d").build();
        }
        return Executors.defaultThreadFactory();
    }
}
