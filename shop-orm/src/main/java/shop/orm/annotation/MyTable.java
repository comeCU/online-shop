package shop.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 数据库表名注解
 * @author dong
 * @date Aug 24, 2019 1:56:03 PM
 */
@Target(value = ElementType.TYPE)  // 作用于类的主键
@Retention(value = RetentionPolicy.RUNTIME)  // 运行时有效
public @interface MyTable {
    /**
     * 表名
     * @return
     */
    public String tableName() default "";

}
