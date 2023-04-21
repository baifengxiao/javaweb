package cn.sc.love.servlet.Servlet;


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
@WebServlet("/fruit.do")
public class ServletOptimize extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setAttribute("operate", "C");
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

        //StreamApi写法
        //TODO foreach是消费型接口，没法用return中止程序
//        Arrays.stream(methods).forEach(method -> {
//            String methodName = method.getName();
//
//            if (methodName.equals(operate)) {
//                try {
//                    method.invoke(this, req, resp);
//
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            } else {
//                throw new RuntimeException("operation值非法!");
//            }
//
//        });


    }


//    private void C(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("C...");
//    }
//
//
//    private void R(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("R...");
//
//    }
//
//    private void U(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("U...");
//    }
//
//    private void D(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        System.out.println("D...");
//    }


}
