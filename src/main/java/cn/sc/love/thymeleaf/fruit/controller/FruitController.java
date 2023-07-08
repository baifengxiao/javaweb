package cn.sc.love.thymeleaf.fruit.controller;

import cn.sc.love.servlet.util.StringUtil;
import cn.sc.love.thymeleaf.fruit.serive.FruitService;
import cn.sc.love.thymeleaf.fruit.pojo.Fruit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author yupengtao
 * @Date 2023/7/5 18:23
 **/
public class FruitController {
//    private FruitDAO fruitDAO = new FruitDAOImpl();
private FruitService fruitService=null;

    private String update(Integer fid, String fname, Integer price, Integer fcount, String remark) {

//        //2.获取参数
//        String fidStr = request.getParameter("fid");
//        Integer fid = Integer.parseInt(fidStr);
//        String fname = request.getParameter("fname");
//        String priceStr = request.getParameter("price");
//        int price = Integer.parseInt(priceStr);
//        String fcountStr = request.getParameter("fcount");
//        Integer fcount = Integer.parseInt(fcountStr);
//        String remark = request.getParameter("remark");

        //3.执行更新
        fruitService.updateFruit(new Fruit(fid, fname, price, fcount, remark));

        //4.资源跳转
//        response.sendRedirect("fruit.do");
        //不单个直接跳转，而是返回字符串
        return "redirect:fruit.do";
    }

    private String edit(Integer fid, HttpServletRequest request) {

        if (fid != null) {

            Fruit fruit = fruitService.getFruitByFid(fid);
            request.setAttribute("fruit", fruit);
            return "edit";
        }
        return "error";
    }

    private String del(Integer fidStr) {

        if (fidStr != null) {

            fruitService.delFruit(fidStr);

            //super.processTemplate("index",request,response);
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String add(Integer fid, String fname, Integer price, Integer fcount, String remark) throws IOException {

        Fruit fruit = new Fruit(0, fname, price, fcount, remark);
        fruitService.addFruit(fruit);
        return "redirect:fruit.do";
    }

    private String index(String oper,String keyword, Integer pageNo, HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (pageNo==null){
            pageNo=1;
        }
        //如果oper!=null 说明 通过表单的查询按钮点击过来的
        //如果oper是空的，说明 不是通过表单的查询按钮点击过来的
        if (StringUtil.isNotEmpty(oper) && "search".equals(oper)) {
            pageNo = 1;
            if (StringUtil.isEmpty(keyword)) {
                keyword = "";
            }
            //将keyword保存（覆盖）到session中
            session.setAttribute("keyword", keyword);
        } else {
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj != null) {
                keyword = (String) keywordObj;
            } else {
                keyword = "";
            }
        }

        // 重新更新当前页的值
        session.setAttribute("pageNo", pageNo);

//        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitService.getFruitList(keyword, pageNo);
        session.setAttribute("fruitList", fruitList);

        //总记录条数
        int pageCount = fruitService.getPageCount(keyword);
        //总页数
//        int pageCount = (fruitCount + 5 - 1) / 5;

        session.setAttribute("pageCount", pageCount);

        return "index";
    }

}

