package goodat.weaving;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;

/**
 * 类描述：某个方法之前、以及之后进行hook 适合埋点操作
 *
 * @author Created by luckyAF on 2019-03-21
 */

@Target({METHOD, CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface HookMethod {
    String beforeMethod() default "";
    String afterMethod() default "";
}
