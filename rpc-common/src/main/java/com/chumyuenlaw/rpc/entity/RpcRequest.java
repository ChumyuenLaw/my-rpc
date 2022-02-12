package com.chumyuenlaw.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable
{
    private String interfaceName;

    private String methodName;

    private Object[] parameters;

    private Class<?>[] paramTypes;
}
