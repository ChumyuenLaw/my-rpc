package com.chumyuenlaw.rpc.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * SerializerCode 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/12 16:36
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@AllArgsConstructor
@Getter
public enum SerializerCode
{
    KRYO(0),
    JSON(1),
    HESSIAN(2),
    PROTOSTUFF(3);

    private final int code;
}
