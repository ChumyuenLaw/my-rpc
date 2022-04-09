package com.chumyuenlaw.canteenmanagementserver2;

import com.chumyuenlaw.rpc.annotation.Service;
import com.chumyuenlaw.rpc.api.CanteenService;
import com.chumyuenlaw.rpc.api.Dish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * <pre>
 * CanteenServiceImpl 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/9 16:32
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
@Component
@org.springframework.stereotype.Service
public class CanteenServiceImpl implements CanteenService
{
    private static Logger logger = LoggerFactory.getLogger(CanteenServiceImpl.class);

    @Autowired
    private CanteenMapper canteenMapper;

    public static CanteenServiceImpl canteenServiceImpl;

    @PostConstruct
    public void init()
    {
        canteenServiceImpl = this;
        canteenServiceImpl.canteenMapper = this.canteenMapper;
    }

    @Override
    public List<Dish> findDishesList()
    {
        logger.info("Method findDishesList was invoked from Server 2");
        return canteenServiceImpl.canteenMapper.findDishesList();
    }

    @Override
    public Dish findDishById(int id)
    {
        return canteenServiceImpl.canteenMapper.findDishById(id);
    }

    @Override
    public void deleteDishById(int id)
    {
        canteenServiceImpl.canteenMapper.deleteDishById(id);
    }

    @Override
    public void insertDish(Dish dish)
    {
        canteenServiceImpl.canteenMapper.insertDish(dish);
    }

    @Override
    public void updateDish(Dish dish)
    {
        canteenServiceImpl.canteenMapper.updateDish(dish);
    }
}
