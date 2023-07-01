package cn.sc.love.thymeleaf.servlet;

import cn.sc.love.thymeleaf.fruit.dao.FruitDAO;
import cn.sc.love.thymeleaf.fruit.dao.impl.FruitDAOImpl;
import cn.sc.love.thymeleaf.fruit.pojo.Fruit;
import cn.sc.love.thymeleaf.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author yupengtao
 * @Date 2023/7/1 20:05
 **/
//Servlet从3.0版本开始支持注解方式的注册
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    public void doGet(HttpServletRequest request , HttpServletResponse response)throws IOException, ServletException {
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList();
        //保存到session作用域
        HttpSession session = request.getSession() ;
        session.setAttribute("fruitList",fruitList);
        System.out.println(fruitList);
        //此处的视图名称是 index
        //那么thymeleaf会将这个 逻辑视图名称 对应到 物理视图 名称上去
        //逻辑视图名称 ：   index
        //物理视图名称 ：   view-prefix + 逻辑视图名称 + view-suffix
        //所以真实的视图名称是：      /       index       .html
        super.processTemplate("fruit",request,response);
    }
}
