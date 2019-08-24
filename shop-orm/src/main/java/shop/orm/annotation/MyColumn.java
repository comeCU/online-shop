package shop.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 字段注解
 * @author dong
 * @date Aug 24, 2019 1:55:03 PM
 */
@Target(value = ElementType.FIELD)  // 作用于属性
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyColumn {
    /**
     * 列名
     * @return
     */
    public String columnName();
    /**
     * 类型长度
     * @return
     */
    public int length() default 50;
}
