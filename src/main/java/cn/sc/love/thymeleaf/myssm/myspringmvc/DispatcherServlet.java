package cn.sc.love.thymeleaf.myssm.myspringmvc;

import cn.sc.love.servlet.util.StringUtil;
import cn.sc.love.thymeleaf.myssm.ioc.BeanFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author yupengtao
 * @Date 2023/7/2 14:26
 **/

@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

private BeanFactory beanFactory;

    public DispatcherServlet() {
    }

    public void init() throws ServletException {
        super.init();
//        try {
//            beanFactory=new ClassPathXmlApplicationContext();
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }

        ServletContext application = getServletContext();
        Object beanFactoryObj = application.getAttribute("beanFactory");
        if (beanFactoryObj!=null){
            beanFactory=(BeanFactory) beanFactoryObj;
        }else {
            throw new RuntimeException("IOC容器获取失败!");
        }
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("UTF-8");
        //假设url是：  http://localhost:8080/pro15/hello.do
        //那么servletPath是：    /hello.do
        // 我的思路是：
        // 第1步： /hello.do ->   hello   或者  /fruit.do  -> fruit
        // 第2步： hello -> HelloController 或者 fruit -> FruitController
        String servletPath = request.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastDotIndex);

        Object controllerBeanObj = beanFactory.getBean(servletPath);

        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                //1,统一获取请求参数
                Parameter[] parameters = method.getParameters();

                Object[] parameterValues = new Object[parameters.length];

                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    String parameterName = parameter.getName();
                    if ("request".equals(parameterName)) {
                        parameterValues[i] = request;
                    } else if ("response".equals(parameterName)) {
                        parameterValues[i] = response;
                    } else if ("session".equals(parameterName)) {
                        parameterValues[i] = request.getSession();
                    } else {

                        String parameterValue = request.getParameter(parameterName);
                        //不考虑复选框的情况
                        String typeName = parameter.getType().getName();

                        Object parameterObj=parameterValue;
                        //简单举例包装类到string的转换问题，以Integer为例
                        if (parameterObj!=null){
                            if ("java.lang.Integer".equals( typeName)){
                                parameterObj=Integer.parseInt(parameterValue);
                            }
                        }

                        parameterValues[i] = parameterObj;
                    }
                }

                //2,controller组件中的方法字符串调用
                method.setAccessible(true);
                Object returnObj = method.invoke(controllerBeanObj, parameterValues);

                //3.试图处理，完成重定向
                String methodReturnStr = (String) returnObj;
                if (methodReturnStr.startsWith("redirect:")) {
                    String redirectStr = methodReturnStr.substring("redirect:".length());
                    response.sendRedirect(redirectStr);
                } else {
                    super.processTemplate(methodReturnStr, request, response);
                }


            }
            Method method = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class);

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
