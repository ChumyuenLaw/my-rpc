package com.chumyuenlaw.rpc.provider;

/**
 * <pre>
 * ServiceProvider 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/24 16:38
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface ServiceProvider
{
    <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);
}
