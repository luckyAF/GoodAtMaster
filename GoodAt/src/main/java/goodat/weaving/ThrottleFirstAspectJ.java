package goodat.weaving;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 类描述：间隔
 *
 * @author Created by luckyAF on 2019-03-21
 */
@Aspect
public class ThrottleFirstAspectJ {
    @Pointcut("within(@goodat.weaving.ThrottleFirst *)")
    public void withinAnnotatedClass() {
    }

    @Pointcut("execution(!synthetic * *(..)) && withinAnnotatedClass()")
    public void methodInsideAnnotatedType() {
    }

    @Pointcut("execution(@goodat.weaving.ThrottleFirst * *(..)) || methodInsideAnnotatedType()")
    public void method() {
    }

    @Around("method()")
    public Object aroundJoinPoint(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ThrottleFirst hookMethod = method.getAnnotation(ThrottleFirst.class);
        boolean hasReturnType = signature.getReturnType() != void.class;
        // 如果有返回值 不能拦截
        Object result = null;
        if(hasReturnType){
            result = joinPoint.proceed();
        }else if (!GoodAt.isFastDoubleCall(method.getName(), hookMethod.value())) {
            //不是多次调用，执行原方法
            joinPoint.proceed();
        }
        return result;

    }

}
