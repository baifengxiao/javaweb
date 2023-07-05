package cn.sc.love.thymeleaf.servlet;

/**
 * @Author yupengtao
 * @Date 2023/7/2 13:44
 **/


import cn.sc.love.thymeleaf.fruit.dao.FruitDAO;
import cn.sc.love.thymeleaf.fruit.dao.impl.FruitDAOImpl;
import cn.sc.love.thymeleaf.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.sun.javafx.fxml.expression.Expression.add;


/**
 * @author YPT
 * @create 2023-04-22-2:30
 */
public class FruitController extends ViewBaseServlet {

    //之前FruitServlet是一个Sevlet组件，那么其中的init方法一定会被调用
    //之前的init方法内部会出现一句话：super.init();

    private FruitDAO fruitDAO = new FruitDAOImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("operate", "fruit");
        String operate = (String) req.getAttribute("operate");
        System.out.println("当前操作是：" + operate);

        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.equals(operate)) {
                try {
                    method.invoke(this, req, resp);
                    return;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

        }




    }





}
