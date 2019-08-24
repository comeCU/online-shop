package shop.dao;

import java.util.List;
import shop.model.Order;
/**
 * 管理员dao层接口
 * @author dong
 * @date Aug 24, 2019 2:43:05 PM
 */
public interface AdminDao {
    /**
     * 查询所有订单信息
     * @param sql
     * @return
     */
    public List<Order> query(String sql);
    
    /**
     * 删除某条订单
     * @param sql
     * @return
     */
    public boolean delete(String sql);
}
