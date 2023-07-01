package cn.sc.love.demo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author yupengtao
 * @Date 2023/7/1 22:22
 **/
@WebServlet("/demo01")
public class demo01 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("uname", "1111");
//        resp.sendRedirect("demo02");
//        System.out.println("重定向，取不到");
//        System.out.println("转发，去得到");
        req.getRequestDispatcher("demo02").forward(req, resp);
    }
}
