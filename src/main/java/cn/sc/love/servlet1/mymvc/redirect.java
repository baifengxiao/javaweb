package cn.sc.love.servlet1.mymvc;

import javax.servlet.http.HttpServletRequest;

/**
 * @author YPT
 * @create 2023-04-22-18:43
 */
public class redirect extends ViewBaseServlet {

    protected String update(HttpServletRequest req) {


        return"redirect:fruit.do" ;
    }
}
