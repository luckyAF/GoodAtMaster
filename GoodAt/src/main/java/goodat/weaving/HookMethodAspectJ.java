package goodat.weaving;

import android.text.TextUtils;
import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import goodat.weaving.internal.ReflectUtils;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2019-03-21
 */
@Aspect
public class HookMethodAspectJ {
    private static final String POINTCUT_METHOD = "execution(@goodat.weaving.HookMethod * *(..))";
    @Around(POINTCUT_METHOD)
    public Object hookMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HookMethod hookMethod= method.getAnnotation(HookMethod.class);
        String beforeMethod = "";
        String afterMethod = "";
        if (hookMethod != null) {
            beforeMethod = hookMethod.beforeMethod();
            afterMethod = hookMethod.afterMethod();
        }

        if (!TextUtils.isEmpty(beforeMethod)) {
            try {
                ReflectUtils.reflect(joinPoint.getTarget()).method(beforeMethod);
            } catch (ReflectUtils.ReflectException e) {
                e.printStackTrace();
                Log.e("hookMethod", "no method " + beforeMethod);
            }
        }
        Object object = joinPoint.proceed();
        if (!TextUtils.isEmpty(afterMethod)) {
            try {
                ReflectUtils.reflect(joinPoint.getTarget()).method(afterMethod);
            } catch (ReflectUtils.ReflectException e) {
                e.printStackTrace();
                Log.e("hockMethod", "no method " + afterMethod);
            }
        }
        return object;
    }
}