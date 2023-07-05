package cn.sc.love;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author yupengtao
 * @Date 2023/7/6 07:26
 **/
@WebServlet(urlPatterns = {"/initDemo"}, initParams = {@WebInitParam(name = "hello", value = "world")})
public class Deme1Servlet extends HttpServlet {

    @Override
    public void init() {
        ServletConfig config = getServletConfig();
        String initValue = config.getInitParameter("hello");
        System.out.println("initValue = " + initValue);
        String uname = config.getInitParameter("uname");
        ;
        System.out.println("uname = " + uname);

        //演示下获取上下文参数，此时不用理解这是什么
        ServletContext servletContext = getServletContext();
        //初始化方法中可以获取
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        System.out.println("contextConfigLocation = " + contextConfigLocation);
        //另外，req，session中也可以获取


    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //另外，req，session中也可以获取
        req.getServletContext();
        req.getSession().getServletContext();
    }
}
