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
import shop.orm.exception.MyException;
public class UserInfoDaoImpl implements UserInfoDao {
    DbDao dbDao = new DbDaoImpl();

    @Override
    public List<UserInfo> query(String sql, String[] colums) {
        return dbDao.query(sql, colums);
    }

    @Override
    public int insert(UserInfo userinfo) {
        try {
            return dbDao.save(userinfo).intValue();
        } catch (MyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

}
