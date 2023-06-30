package cn.sc.love.servlet1.Servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author YPT
 * @create 2023-04-21-16:40
 */
public class SessionTest  extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println("session  ID是："+session.getId());
        session.setAttribute("username","张三");
        Object username = session.getAttribute("username");
        System.out.println("你是："+username);

    }
}
