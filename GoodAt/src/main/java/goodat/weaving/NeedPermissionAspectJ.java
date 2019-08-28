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
    @Pointcut("within(@goodat.weaving.NeedPermission *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(!synthetic *.new(..)) && withinAnnotatedClass()")
    public void constructorInsideAnnotatedType() {
    }

    @Pointcut("execution(@goodat.weaving.NeedPermission * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    } //方法切入点

    @Around("(method()) && @annotation(needPermission)")
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

