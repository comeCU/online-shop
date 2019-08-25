package shop.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import shop.dao.BookDao;
import shop.dao.impl.BookDaoImpl;
import shop.model.Book;
import shop.model.PageBean;
import shop.orm.dao.DbDao;
import shop.orm.dao.impl.DbDaoImpl;
import shop.orm.utils.DbUtil;
import shop.service.BookService;
/**
 * 图书service层实现类
 * @author dong
 * @date Aug 25, 2019 6:05:50 PM
 */
public class BookServiceImpl implements BookService {
    BookDao bookDao = new BookDaoImpl();

    @Override
    public PageBean<Book> findAll(int pc, int pr) {
        return bookDao.query(pc, pr);
    }

    @Override
    public List<Book> findBookByName(String bookname, int pc, int pr) {
        // TODO Auto-generated method stub
        return null;
    }

}
