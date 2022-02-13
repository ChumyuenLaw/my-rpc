package com.chumyuenlaw.rpc.serializer;

/**
 * <pre>
 * CommonSerializer 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/12 16:16
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface CommonSerializer
{
    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

    static CommonSerializer getByCode(int code)
    {
        switch (code)
        {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JSONSerializer();
            case 2:
                return new HessianSerializer();
            default:
                return null;
        }
    }
}
