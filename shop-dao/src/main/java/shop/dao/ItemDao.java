package shop.dao;

import shop.model.Item;
/**
 * 订单中每一项图书操作的dao层接口
 * @author dong
 * @date Aug 24, 2019 2:49:56 PM
 */
public interface ItemDao {
    /**
     * 添加订单详细信息
     * @param item
     * @return
     */
    public int insert(Item item);
    /**
     * 查询总记录数
     * @return
     */
    public int count();
}
