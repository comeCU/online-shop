package shop.service;

import java.util.List;
import shop.model.Book;
import shop.model.PageBean;

/**
 * 图书service层
 * @author dong
 * @date Aug 25, 2019 5:43:06 PM
 */
public interface BookService {
    /**
     * 查询所有图书
     * @param pc 当前页码
     * @param pr 每页记录数
     * @return
     */
    public PageBean<Book> findAll(int pc, int pr);
    
    /**
     * 根据书名模糊查询
     * @param bookname
     * @param pc 当前页码
     * @param pr 每页记录数
     * @return
     */
    public List<Book> findBookByName(String bookname,int pc, int pr);
    
}
