package cn.sc.love.thymeleaf.myssm.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yupengtao
 * @Date 2023/7/8 02:56
 **/
public class ClassPathXmlApplicationContext implements BeanFactory {

    private Map<String, Object> beanMap = new HashMap<>();

    //采用之前controller层相同的方法获取xml中配置的对象，不止是controller层
    public ClassPathXmlApplicationContext() {

        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1.创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3.创建Document对象
            Document document = documentBuilder.parse(inputStream);

            //4.获取所有的bean节点
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    Class beanClass = Class.forName(className);
                    Object beanObj = beanClass.newInstance();

                    //仅仅通过反射将bean实例对象保存到map容器中，但是还不知道实例间的关系
                    beanMap.put(beanId, beanObj);
                    //到目前为止，此处需要注意的是，bean和bean之间的依赖关系还没有设置
                }
            }
            //5，组装bean之间的依赖关系
            for (int j = 0; j < beanNodeList.getLength(); j++) {
                Node beanNode = beanNodeList.item(j);
                //如果是元素节点,取出name和ref的值，
                if (beanNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(beanNode.getChildNodes())) {
                    Element propertyElement = (Element) beanNode;
                    String beanId = propertyElement.getAttribute("id");
                    String propertyName = propertyElement.getAttribute("name");
                    String propertyRef = propertyElement.getAttribute("ref");
                    //找到property里面的Ref对应的实例
                    //ref对应beanmap里面的id，所以根据id取出map的值，然后放进properties里面
                    Object refObj = beanMap.get(propertyRef);
//2) 将refObj设置到当前bean对应的实例的property属性上去
                    //2.1获取当前bean实例
                    Object beanObj = beanMap.get(beanId);
                    Class beanClazz = beanObj.getClass();

                    //获取属性名
                    Field propertyField = beanClazz.getDeclaredField(propertyName);
                    propertyField.setAccessible(true);
                    //，refObj是ref实例
                    //反射，给属性注入值
                    propertyField.set(beanObj,refObj);

                }


            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Object getBean(String id) {
        return beanMap;
    }
}
