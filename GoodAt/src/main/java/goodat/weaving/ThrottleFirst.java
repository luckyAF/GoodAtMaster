package goodat.weaving;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * 类描述：在一段间隔内只允许调用一次  throttleFirst
 *
 * @author Created by luckyAF on 2019-03-21
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(METHOD)
public @interface ThrottleFirst {

    long DEFAULT_INTERVAL_MILLIS = 1000;

    /**
     * @return 再次调用的间隔（ms），默认是1000ms
     */
    long value() default DEFAULT_INTERVAL_MILLIS;
}
