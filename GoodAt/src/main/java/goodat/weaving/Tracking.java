package goodat.weaving;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 类描述：检查
 *
 * @author Created by luckyAF on 2019-03-21
 */

@Target({TYPE, METHOD, CONSTRUCTOR})
@Retention(RUNTIME)
public @interface Tracking {
    /**
     * @return 日志的优先级(默认是0，-1则打log)
     */
    int priority() default 0;
}
