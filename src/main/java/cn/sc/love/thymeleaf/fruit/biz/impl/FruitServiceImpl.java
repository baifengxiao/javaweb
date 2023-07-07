package cn.sc.love.thymeleaf.fruit.biz.impl;

import cn.sc.love.thymeleaf.fruit.biz.FruitService;
import cn.sc.love.thymeleaf.fruit.dao.FruitDAO;
import cn.sc.love.thymeleaf.fruit.dao.impl.FruitDAOImpl;
import cn.sc.love.thymeleaf.fruit.pojo.Fruit;

import java.util.List;

/**
 * @Author yupengtao
 * @Date 2023/7/7 18:19
 **/
public class FruitServiceImpl implements FruitService {

    //service实现serviceimpl，serviceimpl调dao层，dao调用daoimpl实现
    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    public List<Fruit> getFruitList(String keyword, Integer pageNo) {
        return fruitDAO.getFruitList(keyword, pageNo);
    }

    @Override
    public void addFruit(Fruit fruit) {
        fruitDAO.addFruit(fruit);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return fruitDAO.getFruitByFid(fid);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.delFruit(fid);
    }

    @Override
    public Integer getPageCount(String keyword) {
        int count = fruitDAO.getFruitCount(keyword);
        int pageCount = (count + 5 - 1) / 5;
        return pageCount;
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDAO.updateFruit(fruit);
    }
}
