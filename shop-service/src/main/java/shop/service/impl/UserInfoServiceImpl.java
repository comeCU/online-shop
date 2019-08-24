package shop.service.impl;

import java.util.List;
import shop.dao.UserInfoDao;
import shop.dao.impl.UserInfoDaoImpl;
import shop.service.UserInfoService;
/**
 * UserInfoService实现类
 * @author dong
 * @date Aug 24, 2019 8:38:54 PM
 */
public class UserInfoServiceImpl implements UserInfoService {
    private UserInfoDao userInfoDao = new UserInfoDaoImpl();
    
    @Override
    public boolean checkLogin(String username, String password) {
        String sql = "select * from t_userinfo where username='"+username+"'and password='"+password+"'";
        List list = userInfoDao.query(sql);
        
        return list.size()>0 ? true : false;
    }

}
