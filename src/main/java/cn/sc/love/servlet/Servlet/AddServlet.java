package cn.sc.love.servlet.Servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YPT
 * @create 2023-04-20-22:36
 */
public class AddServlet extends HttpServlet {

    protected void doPost( String fname,Integer price, Integer fcount, String remark  ) throws ServletException, IOException {

        System.out.println("fname=" + fname);
        System.out.println("price=" + price);
        System.out.println("fcount=" + fcount);
        System.out.println("remark=" + remark);
    }
}
