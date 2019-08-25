package shop.service.impl;

import java.util.List;
import shop.dao.UserInfoDao;
import shop.dao.impl.UserInfoDaoImpl;
import shop.model.UserInfo;
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
        String[] colums = {"username","password","email"};
        List list = userInfoDao.query(sql, colums);
        
        return list.size()>0 ? true : false;
    }

    @Override
    public boolean addUser(UserInfo userInfo) {
        return userInfoDao.insert(userInfo) > 0 ? true : false;
    }

    @Override
    public boolean userExist(String username) {
        String sql = "select * from t_userinfo where username='" + username + "'";
        System.out.println(sql);
        String[] colums = {"username"};
        return userInfoDao.query(sql, colums).size() > 0 ? true : false;
    }

}
