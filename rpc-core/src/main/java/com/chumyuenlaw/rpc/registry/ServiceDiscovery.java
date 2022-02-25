package com.chumyuenlaw.rpc.registry;

import java.net.InetSocketAddress;

/**
 * <pre>
 * ServiceDiscovery 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/25 15:33
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface ServiceDiscovery
{
    InetSocketAddress lookupService(String serviceName);
}
