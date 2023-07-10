package cn.sc.love.thymeleaf.myssm.basedao;

/**
 * @Author yupengtao
 * @Date 2023/7/10 16:09
 **/
public class DAOException extends RuntimeException{
    public DAOException(String msg){
        super(msg);
    }
}