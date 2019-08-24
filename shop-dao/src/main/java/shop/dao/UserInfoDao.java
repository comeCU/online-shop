package shop.dao;

import java.util.List;
import shop.model.UserInfo;
/**
 * 用户dao层接口
 * @author dong
 * @date Aug 24, 2019 2:44:18 PM
 */
public interface UserInfoDao {
    /**
     * 查询
     * @param sql
     * @return
     */
    public List<UserInfo> query(String sql);
    /**
     * 插入
     * @param userinfo
     * @return
     */
    public int insert(UserInfo userinfo);
}
