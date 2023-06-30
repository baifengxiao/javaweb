package cn.sc.love.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author yupengtao
 * @Date 2023/6/30 22:41
 **/
public class SessionTest1 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println("session  ID是：" + session.getId());
        //没在会话里存值，也能从session里取出
        Object username = session.getAttribute("usernme");
        System.out.println("你是：" + username);

    }
}
