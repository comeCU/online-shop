package shop.service;

import static org.junit.Assert.*;

import org.junit.Test;
import shop.model.UserInfo;
import shop.service.impl.UserInfoServiceImpl;
/**
 * 测试UserInfoServiceImpl
 * @author dong
 * @date Aug 24, 2019 9:14:30 PM
 */
public class UserInfoServiceImplTest {

    @Test
    public void testCheckLogin() {
        assertEquals(true, new UserInfoServiceImpl().checkLogin("1", "12345678"));
    }
    
    @Test
    public void testAddUser() {
        assertEquals(false, new UserInfoServiceImpl().
                addUser(new UserInfo("bbb", "12345678", "123@qq.com")));
    }
    
    @Test
    public void testUserExist() {
        assertEquals(true, new UserInfoServiceImpl().userExist("aaa"));
    }

}
