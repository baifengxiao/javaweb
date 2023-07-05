package cn.sc.love;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

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
        String uname= config.getInitParameter("uname");;
        System.out.println("uname = " + uname);
    }
}
