package com.chumyuenlaw.rpc.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * SingletonFactory 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/26 16:47
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class SingletonFactory
{
    private static Map<Class, Object> objectMap = new HashMap<>();

    private SingletonFactory()
    {
    }

    public static <T> T getInstance(Class<T> clazz)
    {
        Object instance = objectMap.get(clazz);

        synchronized (clazz)
        {
            if (instance == null)
            {
                try
                {
                    instance = clazz.newInstance();
                    objectMap.put(clazz, instance);
                } catch (InstantiationException | IllegalAccessException  e)
                {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        return clazz.cast(instance);
    }
}
