package shop.service;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;
import shop.model.Book;
import shop.model.PageBean;
import shop.service.impl.BookServiceImpl;
/**
 * 测试BookServiceImpl
 * @author dong
 * @date Aug 25, 2019 6:56:16 PM
 */
public class BookServiceImplTest {

    @Test
    public void testFindAll() {
        PageBean<Book> records = new BookServiceImpl().findAll(4, 3);
        List<Book> beanList = records.getBeanList();
        for (Book book : beanList) {
            System.out.println(book);
        }
    }

    @Test
    public void testFindBookByName() {
        fail("Not yet implemented");
    }

}
