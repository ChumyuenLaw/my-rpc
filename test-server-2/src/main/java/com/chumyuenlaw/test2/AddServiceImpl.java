package com.chumyuenlaw.test2;

import com.chumyuenlaw.rpc.annotation.Service;
import com.chumyuenlaw.rpc.api.AddService;

/**
 * <pre>
 * AddServiceImpl 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/3 20:31
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
public class AddServiceImpl implements AddService
{
    @Override
    public int add(int num, int num2)
    {
        return num + num2;
    }

    @Override
    public int add2(int num, int num2, int num3)
    {
        return num + num2 + num3;
    }
}
