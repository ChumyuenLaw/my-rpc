package com.chumyuenlaw.canteenmanagementclient;

import com.chumyuenlaw.rpc.api.CanteenService;
import com.chumyuenlaw.rpc.api.Dish;
import com.chumyuenlaw.rpc.loadbalancer.RoundRobinLoadBalancer;
import com.chumyuenlaw.rpc.serializer.CommonSerializer;
import com.chumyuenlaw.rpc.transport.RpcClient;
import com.chumyuenlaw.rpc.transport.RpcClientProxy;
import com.chumyuenlaw.rpc.transport.netty.client.NettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 * CanteenController 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/4/9 0:37
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */

@RestController
public class CanteenController
{
    public static CanteenService canteenService;

    static {
        RpcClient canteenClient = new NettyClient(CommonSerializer.DEFAULT_SERIALIZER, new RoundRobinLoadBalancer());
        RpcClientProxy proxy = new RpcClientProxy(canteenClient);
         canteenService = proxy.getProxy(CanteenService.class);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(Integer id)
    {
        canteenService.deleteDishById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void update(Dish dish)
    {
        canteenService.updateDish(dish);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public void insert(Dish dish)
    {
        canteenService.insertDish(dish);
    }

    @RequestMapping(value = "/listDishes")
    @ResponseBody
    public List<Dish> listDishes()
    {
        return canteenService.findDishesList();
    }

    @RequestMapping(value = "/listById")
    public Dish listDishById(Integer id)
    {
        return canteenService.findDishById(id);
    }
}
