package cn.sc.love.thymeleaf.myssm.myspringmvc;

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
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yupengtao
 * @Date 2023/7/2 14:26
 **/
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    Map<String, Object> beanMap = new HashMap<>();

    //解析标签对象，目的是通过:解析后的路径fruit   --->   找到对应的FruitController
    //第2步，读取配置文件，扔到类里面去
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
    //第1步，截取请求路径中的servlet名称
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();

        //去掉/
        servletPath = servletPath.substring(1);
        //去掉.do
        int lastIndexOf = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastIndexOf);
        System.out.println("servletPath = " + servletPath);

        Object controllerBeanObj = beanMap.get(servletPath);

//        req.setAttribute("operate","add");
        //第3步，反射调用这个方法
        String operate = req.getParameter("operate");
        System.out.println(operate);

        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        try {
            Method method = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class, HttpServletResponse.class);
            if (method != null) {
                method.setAccessible(true);
                method.invoke(controllerBeanObj, req, resp);
                return;
            } else {
                throw new RuntimeException("operate值非法!");
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

//          另一种写法，不用这种
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
