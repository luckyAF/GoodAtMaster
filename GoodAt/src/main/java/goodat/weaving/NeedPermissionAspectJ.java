package goodat.weaving;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2019-08-28
 */
@Aspect
public class NeedPermissionAspectJ {
    private static final String POINTCUT_METHOD = "execution(@goodat.weaving.NeedPermission * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void method() {
    }

    @Around("method() && @annotation(needPermission)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, NeedPermission needPermission) {
        //执行请求操作
        GoodAt.requestPermission(needPermission.value(), new GoodAt.ApplyCallback() {
            @Override
            public void onGranted() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
    }

}

