package cn.sc.love.servlet.mymvc;


import cn.sc.love.servlet.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YPT
 * @create 2023-04-22-6:26
 */
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {

    Map<String, Object> beanMap = new HashMap<>();

    public DispatcherServlet() {
        try {
            //读取自己写的配置文件流
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1,创建documentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            //2，创建documentBuilder
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3，获取document对象
            Document document = documentBuilder.parse(inputStream);

            //4,获取所有的bean节点
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanid = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");

                    //5.到此，获取了配置文件中的类对象，
                    Object beanObj = Class.forName(className).newInstance();
                    beanMap.put(beanid, beanObj);
                }
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //截取servlet名称
        req.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();

        String FirstindexOf = servletPath.substring(1);
        int lastIndexOf = servletPath.lastIndexOf(".do");
        String newservletPath = servletPath.substring(0, lastIndexOf);

        Object controllerBeanObj = beanMap.get(servletPath);


        req.setAttribute("operate", "add");
        String operate = req.getParameter("operate");
        System.out.println(operate);
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {


            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                String methodName = method.getName();
                if (methodName.equals(operate)) {


                    {

                        //1，统一获取请求参数

                        //获取当前方法的参数,返回参数数组
                        Parameter[] parameters = method.getParameters();

                        //parametersValues  用于承载参数的值

                        Object[] parameterValues = new Object[parameters.length];
                        for (int i = 0; i < parameters.length; i++) {
                            Parameter parameter = parameters[i];
                            String parameterName = parameter.getName();

                            //特殊值判断
                            if ("request".equals(parameterName)) {
                                parameterValues[i] = req;
                            } else if ("session".equals(parameterName)) {
                                parameterValues[i] = req.getSession();
                            } else {
                                //从请求中获取参数值
                                String parameterValue = req.getParameter(parameterName);
                                String typeName = parameter.getType().getName();
                                Object parameterObj = parameterValue;
                                if (parameterObj != null) {
                                    if ("java.lang.Integer".equals(typeName)) {
                                        parameterObj = Integer.parseInt(parameterValue);

                                    }
                                }

                                parameterValues[i] = parameterObj;

                            }


                        }


                        //2.controller组件中的方法调用
                        method.setAccessible(true);
                        Object returnObj = method.invoke(controllerBeanObj, req);


                        //3.视图处理
                        String methodReturnStr = (String) returnObj;
                        if (methodReturnStr.startsWith("redirect:")) {
                            String redirectStr = methodReturnStr.substring("redirect:".length());
                            resp.sendRedirect(redirectStr);
                        } else {
                            super.processTemplate(methodReturnStr, req, resp);
                        }

                    }


                } else {
                    throw new RuntimeException("operate值非法!");
                }

            }


        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


//        Method[] methods = declaredMethods;
//
//
//        Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
//        for (Method method : methods) {
//            String methodName = method.getName();
//            if (methodName.equals(operate)) {
//                try {
//                    method.invoke(this, req, resp);
//                    return;
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                } catch (InvocationTargetException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//        }

    }
}
