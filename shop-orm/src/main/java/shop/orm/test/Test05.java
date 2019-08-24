package shop.orm.test;

import java.util.List;
import java.util.Map;
import shop.orm.dao.DbDao;
import shop.orm.dao.impl.DbDaoImpl;

/**
 * 测试 查询操作
 * @author dong
 * @date Jul 11, 2019 7:47:10 PM
 */
public class Test05 {

    public static void main(String[] args) {
        // 测试封装的数据库查询方法
        DbDao dao = new DbDaoImpl();
        String sql = "select s.stu_id stuId, s.stu_name stuName, s.stu_no stuNo, stu_age stuAge, stu_birth stuBirth from t_student as s where stu_id < ? or stu_id = ?";
        List<Map<String, Object>> result = dao.executeSelect(sql, new Object[] {2004, 55265});
        
        System.out.println(result);
        
        /*DbDao dao = new DbDaoImpl();
        Class clazz = Student.class;
        try {
            Object objectById = dao.getObjectById(2002, clazz);
            System.out.println(objectById);  // 输出对象， 存放在链表中
        } catch (MyException e) {
            int code = e.getCode();
            System.out.println(e.getMessage() + "错误码：" + code);
        }*/
        
    }

}
