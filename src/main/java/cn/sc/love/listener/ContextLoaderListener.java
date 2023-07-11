package cn.sc.love.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Author yupengtao
 * @Date 2023/7/11 14:09
 **/
@WebListener
//实现对应的：Listener的接口(我们这里使用的是ServletContextListener),并且实现它里面的方法
public class ContextLoaderListener implements ServletContextListener {
    //     1.1 contextInitialized()这个方法在ServletContext对象被创建出来的时候执行，也就是说在服务器启动的时候执行
// *    1.2 contextDestroyed()这个方法会在ServletContext对象被销毁的时候执行，也就是说在服务器关闭的时候执行
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        System.out.println("在服务器启动的时候，模拟创建SpringMVC的核心容器...");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("在服务器启动的时候，模拟销毁SpringMVC的核心容器...");
    }
}
