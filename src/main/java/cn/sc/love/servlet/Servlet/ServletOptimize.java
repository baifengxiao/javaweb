package cn.sc.love.servlet.Servlet;


import cn.sc.love.servlet.lib.StringUtil;

import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (StringUtil.isNotEmpty(operate)) {
            switch (operate) {
                case "C":
                    C(req, resp);
                    break;

                case "R":
                    R(req, resp);
                    break;
                case "U":
                    U(req, resp);
                    break;
                case "D":
                    D(req, resp);
                    break;
                default:
                    throw new RuntimeException("operation值非法!");
            }
        }


    }


    private void C(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("C...");
    }


    private void R(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("R...");

    }

    private void U(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("U...");
    }

    private void D(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("D...");
    }


}
