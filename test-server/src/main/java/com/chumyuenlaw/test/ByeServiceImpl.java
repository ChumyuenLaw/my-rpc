package com.chumyuenlaw.test;

import com.chumyuenlaw.rpc.annotation.Service;
import com.chumyuenlaw.rpc.api.ByeService;

/**
 * <pre>
 * ByeServiceImpl 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/28 18:20
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
public class ByeServiceImpl implements ByeService
{
    @Override
    public String bye(String message)
    {
        return "Bye, " + message;
    }
}
