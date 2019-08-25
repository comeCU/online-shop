package shop.dao.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import shop.dao.BookDao;
import shop.model.Book;
import shop.model.PageBean;
import shop.orm.dao.DbDao;
import shop.orm.dao.impl.DbDaoImpl;
import shop.orm.utils.DbUtil;
/**
 * 图书dao层实现类
 * @author dong
 * @date Aug 25, 2019 6:08:43 PM
 */
public class BookDaoImpl implements BookDao {
    DbDao dbDao = new DbDaoImpl();

    @Override
    public PageBean<Book> query(int pc, int pr) {
        PageBean<Book> pb=new PageBean<>();
        pb.setPc(pc);
        pb.setPr(pr);
        int tr = 0;
        
        Connection conn = DbUtil.getConnOne();
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        String sql = "select * from t_books";
        try {
            prstmt = conn.prepareStatement(sql);
            rs = prstmt.executeQuery();
            while(rs.next()) { 
                tr++; 
            }
            System.out.println("总记录数：" + tr);
            pb.setTr(tr);
            
            sql="select bid bid, bookname bookname, price price, image image, stock stock "
                    + "from t_books order by bid limit ?,?";
            Object[] params={(pc-1)*pr,pr};
            
            List<Map<String, Object>> beanListMaps = dbDao.executeSelect(sql, params);
            
            System.out.println(beanListMaps);

            List<Book> cusList = new ArrayList<Book>();
            for (Map<String, Object> beanMap : beanListMaps) {
                Gson gson = new Gson();
                String json = gson.toJson(beanMap);  // map转json
                System.out.println("json:" + json);
                Book cus = gson.fromJson(json, Book.class);  // json 转对象. 这里cus_init_time有问题
                System.out.println("cus:" + cus);
                cusList.add(cus);  // 将对象添加到list
            }
            pb.setBeanList(cusList);
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeOne();
        }
        
        return pb;
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int update(String sql) {
        // TODO Auto-generated method stub
        return 0;
    }

}
