package goodat.weaving;

/**
 * 类描述：权限请求
 *
 * @author Created by luckyAF on 2019-03-21
 */


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

/**
 * 请在activity 或fragment里使用！
 * @author Created by luckyAF
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD, PARAMETER})
public @interface NeedPermission {
    /**
     * @return 需要申请权限的集合
     */
    String[] value();
}
