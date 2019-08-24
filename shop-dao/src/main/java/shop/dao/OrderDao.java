package shop.dao;

import java.util.List;

import shop.model.Order;
/**
 * 订单dao层操作接口
 * @author dong
 * @date Aug 24, 2019 2:53:18 PM
 */
public interface OrderDao {
    /**
     * 查询所有订单
     * @param sql
     * @return
     */
    public List query(String sql);
    /**
     * 添加订单方法
     * @param order
     * @return
     */
    public int insert(Order order);
    /**
     * 查询数量
     * @return
     */
    public int count();
    /**
     * 根据用户名查询相应的订单
     * @param username
     * @return
     */
    public List queryByusername(String username);
}
