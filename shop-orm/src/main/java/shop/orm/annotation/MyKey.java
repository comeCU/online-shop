package shop.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 主键注解
 * @author dong
 * @date Aug 24, 2019 1:55:52 PM
 */
@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface MyKey {
    /**
     * 主键列名
     * @return
     */
    public String columnName();
    /**
     * 是否自动增长，默认为false
     * @return
     */
    public boolean isGenerator() default false;

}
