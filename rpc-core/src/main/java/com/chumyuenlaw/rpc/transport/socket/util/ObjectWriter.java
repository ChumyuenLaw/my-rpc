package com.chumyuenlaw.rpc.transport.socket.util;

import com.chumyuenlaw.rpc.entity.RpcRequest;
import com.chumyuenlaw.rpc.enumeration.PackageType;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <pre>
 * ObjectWriter 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/13 20:21
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ObjectWriter
{
    private static final Logger logger = LoggerFactory.getLogger(ObjectWriter.class);

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static void writeObject(OutputStream out, Object object, CommonSerializer serializer) throws IOException
    {
        out.write(intToBytes(MAGIC_NUMBER));

        if (object instanceof RpcRequest)
            out.write(intToBytes(PackageType.REQUEST_PACKAGE.getCode()));
        else
            out.write(intToBytes(PackageType.RESPONSE_PACKAGE.getCode()));

        out.write(intToBytes(serializer.getCode()));

        byte[] bytes = serializer.serialize(object);
        out.write(intToBytes(bytes.length));

        out.write(bytes);

        out.flush();
    }

    private static byte[] intToBytes(int value)
    {
        byte[] bytes = new byte[4];
        bytes[0] = (byte)((value >> 24) & 0xFF);
        bytes[1] = (byte)((value >> 16) & 0xFF);
        bytes[2] = (byte)((value >> 8) & 0xFF);
        bytes[3] = (byte)(value & 0xFF);
        return bytes;
    }
}
