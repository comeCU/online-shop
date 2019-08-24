package shop.dao.impl;

import java.util.List;

import shop.dao.UserInfoDao;
import shop.model.UserInfo;
/**
 * UserInfoDao接口实现类
 * @author dong
 * @date Aug 24, 2019 8:13:37 PM
 */
import shop.orm.dao.DbDao;
import shop.orm.dao.impl.DbDaoImpl;
public class UserInfoDaoImpl implements UserInfoDao {
    DbDao dbDao = new DbDaoImpl();

    @Override
    public List<UserInfo> query(String sql) {
        String[] colums = {"username","password","email"};
        
        return dbDao.query(sql, colums);
    }

    @Override
    public int insert(UserInfo userinfo) {
        // TODO Auto-generated method stub
        return 0;
    }

}
