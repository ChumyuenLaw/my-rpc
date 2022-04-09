package com.chumyuenlaw.rpc.api;

/**
 * <pre>
 * Dish 类
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
public class Dish
{
    private int dishId;
    private String dishName;
    private String cuisine;
    private String offeredLocation;
    private int price;

    public int getDishId()
    {
        return dishId;
    }

    public void setDishId(int dishId)
    {
        this.dishId = dishId;
    }

    public String getDishName()
    {
        return dishName;
    }

    public void setDishName(String dishName)
    {
        this.dishName = dishName;
    }

    public String getCuisine()
    {
        return cuisine;
    }

    public void setCuisine(String cuisine)
    {
        this.cuisine = cuisine;
    }

    public String getOfferedLocation()
    {
        return offeredLocation;
    }

    public void setOfferedLocation(String offeredLocation)
    {
        this.offeredLocation = offeredLocation;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }
}
