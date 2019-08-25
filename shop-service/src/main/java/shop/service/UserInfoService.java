package shop.service;

import shop.model.UserInfo;

/**
 * UserInfo service层接口
 * @author dong
 * @date Aug 24, 2019 8:38:27 PM
 */
public interface UserInfoService {
    /**
     * 验证登录
     * @param username
     * @param password
     * @return 存在该用户返回true，否则返回false
     */
    public boolean checkLogin(String username,String password);
    
    /**
     * 添加用户
     * @param userInfo
     * @return 添加成功返回true，否则返回false
     */
    public boolean addUser(UserInfo userInfo);
    
    /**
     * 判断用户是否已经存在
     * @param username
     * @return
     */
    public boolean userExist(String username);
}
