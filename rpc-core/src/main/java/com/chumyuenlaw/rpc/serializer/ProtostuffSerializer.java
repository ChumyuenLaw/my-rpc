package com.chumyuenlaw.rpc.serializer;

import com.chumyuenlaw.rpc.enumeration.SerializerCode;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <pre>
 * ProtostuffSerializer 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/14 21:26
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ProtostuffSerializer implements CommonSerializer
{
    private static final Logger logger = LoggerFactory.getLogger(ProtostuffSerializer.class);

    private LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    private Map<Class<?>, Schema<?>> schemaCache = new ConcurrentHashMap<>();

    @Override
    public byte[] serialize(Object obj)
    {
        Class clazz = obj.getClass();
        Schema schema = getSchema(clazz);
        byte[] data;
        try
        {
            data = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } finally
        {
            buffer.clear();
        }
        return data;
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz)
    {
        Schema schema = getSchema(clazz);
        Object obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }

    @Override
    public int getCode()
    {
        return SerializerCode.valueOf("PROTOSTUFF").getCode();
    }

    private Schema getSchema(Class clazz)
    {
        Schema schema = schemaCache.get(clazz);
        if (Objects.isNull(schema))
        {
            schema = RuntimeSchema.getSchema(clazz);
            if (Objects.nonNull(schema))
            {
                schemaCache.put(clazz, schema);
            }
        }
        return schema;
    }
}
