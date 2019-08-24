package shop.orm.test;

import shop.orm.dao.DbDao;
import shop.orm.dao.impl.DbDaoImpl;
import shop.orm.exception.MyException;

/**
 * 测试 传入java对象，更新数据库表记录
 * t_studnet 表stu_id 为自增主键
 * @author dong
 * @date Jul 11, 2019 9:39:10 AM
 */
public class Test01 {

    public static void main(String[] args) {
        /*Student student = new Student();
        DbDao dao = new DbDaoImpl();
        
        student.setStuId(55555L);
        student.setStuName("dong022");
        student.setStuNo("20022");
        student.setStuAge(20);
        
        System.out.println("执行前：");
        System.out.println(student);
        try {
            dao.save(student);
        } catch (MyException e) {  // 自定义异常处理
            int code = e.getCode();
            System.out.println(e.getMessage() + "错误码：" + code);
        }
        
        System.out.println("执行后：");
        System.out.println(student);*/
    }

}
