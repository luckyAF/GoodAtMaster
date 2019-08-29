package goodat.weaving;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


/**
 * 类描述：
 *
 * @author Created by luckyAF on 2019/3/25
 */
@Aspect
public class InterceptAspectJ {

    private static final String POINTCUT_METHOD = "execution(@goodat.weaving.Intercept * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void method() {
    }

    @Around("method() && @annotation(intercept)")
    public Object aroundJoinPoint(ProceedingJoinPoint joinPoint, Intercept intercept) throws Throwable {
        //执行拦截操作
        boolean result = proceedIntercept(intercept.value(), joinPoint);
        Log.d("Interceptor","拦截结果:" + result + ", 切片" + (result ? "被拦截！" : "正常执行！"));
        return result ? null : joinPoint.proceed();
    }
    /**
     * 执行拦截操作
     *
     * @param types     拦截的类型集合
     * @param joinPoint 切片
     * @return {@code true}: 拦截切片的执行 <br>{@code false}: 不拦截切片的执行
     */
    private boolean proceedIntercept(int[] types, JoinPoint joinPoint) throws Throwable {
        for (int type : types) {
            //拦截执行
            if (GoodAt.intercept(type)) {
                return true;
            }
        }
        return false;
    }

}
