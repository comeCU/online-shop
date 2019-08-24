package shop.orm.test;
/**
 * 测试 传入java对象，更新数据库表记录
 * t_person 表per_no 为非自增主键
 * @author dong
 * @date Jul 11, 2019 10:11:40 AM
 */
public class Test02 {

    public static void main(String[] args) {
        /*Person person = new Person();
        DbDao dao = new DbDaoImpl();
        
        person.setPerNo("201905");
        person.setPerName("haha05");
        person.setAge(20);
        person.setSalary(13000);
        
        System.out.println("执行前：");
        System.out.println(person);
        try {
            dao.save(person);
        } catch (MyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("执行后：");
        System.out.println(person);*/
        
        
        // 测试表不存在错误
        /*Person2 person = new Person2();
        DbDao dao = new DbDaoImpl();
        
        person.setPerNo("201905");
        person.setPerName("haha05");
        person.setAge(20);
        person.setSalary(13000);
        
        System.out.println("执行前：");
        System.out.println(person);
        try {
            dao.save(person);
        } catch (MyException e) {  // 自定义异常处理
            int code = e.getCode();
            System.out.println(e.getMessage() + "错误码：" + code);
        }
        
        System.out.println("执行后：");
        System.out.println(person);*/
    }

}
