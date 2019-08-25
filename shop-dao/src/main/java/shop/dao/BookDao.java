package shop.dao;

import java.util.List;

import shop.model.Book;
import shop.model.PageBean;
/**
 * 图书dao层接口
 * @author dong
 * @date Aug 24, 2019 2:46:24 PM
 */
public interface BookDao {
    /**
     * 查询所有图书
     * @param sql
     * @return
     */
    public PageBean<Book> query(int pc, int pr);
    /**
     * 查询书籍总数
     * @return
     */
    public int count();
    
    /**
     * 修改商品库存，防止库存为负数
     * @param sql
     * @return
     */
    public int update(String sql);
}
