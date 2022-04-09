package com.chumyuenlaw.rpc.api;

import java.util.List;

/**
 * <pre>
 * CanteenService 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/8 21:53
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface CanteenService
{
    List<Dish> findDishesList();

    Dish findDishById(int id);

    void deleteDishById(int id);

    void insertDish(Dish dish);

    void updateDish(Dish dish);
}
