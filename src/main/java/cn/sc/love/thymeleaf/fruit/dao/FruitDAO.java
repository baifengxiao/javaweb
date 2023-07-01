package cn.sc.love.thymeleaf.fruit.dao;

import cn.sc.love.thymeleaf.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    //获取所有库存列表信息
    List<Fruit> getFruitList();

}

