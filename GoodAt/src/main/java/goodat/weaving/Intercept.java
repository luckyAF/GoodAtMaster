package goodat.weaving;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2019/3/25
 */

@Target({METHOD, CONSTRUCTOR,TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Intercept {
    /**
     * @return 拦截类型
     */
    int[] value();
}
