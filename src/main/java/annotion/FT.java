package annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//实现一个自动化测试注解
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FT {
    String value() default "";
}
