package cn.sc.love.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YPT
 * @create 2023-04-21-17:41
 */
public class TestForward extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("内部转发......");
        System.out.println("转发到04......");
        req.getRequestDispatcher("/04").forward(req, resp);
    }
}
