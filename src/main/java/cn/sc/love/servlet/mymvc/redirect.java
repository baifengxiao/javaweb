package cn.sc.love.servlet.mymvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author YPT
 * @create 2023-04-22-18:43
 */
public class redirect extends ViewBaseServlet {

    protected String update(HttpServletRequest req) {


        return"redirect:fruit.do" ;
    }
}
