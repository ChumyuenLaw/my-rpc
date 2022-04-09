package com.chumyuenlaw.canteenmanagementserver3;

import com.chumyuenlaw.rpc.api.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <pre>
 * CanteenMapper 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/9 16:10
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Mapper
@Repository
public interface CanteenMapper
{
    List<Dish> findDishesList();

    Dish findDishById(int id);

    void deleteDishById(int id);

    void insertDish(Dish dish);

    void updateDish(Dish dish);
}
