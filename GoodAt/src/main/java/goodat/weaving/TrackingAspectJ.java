package goodat.weaving;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import goodat.weaving.internal.StringUtils;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2019-03-21
 */
@Aspect
public class TrackingAspectJ {
    private static char TOP_LEFT_CORNER = '╔';
    private static char BOTTOM_LEFT_CORNER = '╚';
    private static char MIDDLE_CORNER = '╟';
    private static char VERTICAL_DOUBLE_LINE = '║';
    private static String HORIZONTAL_DOUBLE_LINE = "═════════════════════════════════════════════════";
    private static String SINGLE_LINE = "─────────────────────────────────────────────────";
    private static String TOP_BORDER = TOP_LEFT_CORNER + HORIZONTAL_DOUBLE_LINE + HORIZONTAL_DOUBLE_LINE;
    private static String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + HORIZONTAL_DOUBLE_LINE + HORIZONTAL_DOUBLE_LINE;
    private static String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_LINE + SINGLE_LINE;
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    private static final String POINTCUT_METHOD = "execution(@goodat.weaving.Tracking * *(..))";

    @Pointcut(POINTCUT_METHOD)
    public void method() {
    }

    @Around("method() &&  @annotation(tracking)")
    public Object traceMethod(final ProceedingJoinPoint joinPoint,Tracking tracking) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        if (tracking == null || ! GoodAt.isDebug()) {
            return joinPoint.proceed();
        }
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String methodName = codeSignature.getName();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] parameterValues = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        String location = String.format("at %s (%s:%s) ",codeSignature.toShortString(),joinPoint.getSourceLocation().getFileName(),joinPoint.getSourceLocation().getLine());
        StringBuilder logBuilder = new StringBuilder(location);
        logBuilder
                .append("\n")
                .append(TOP_BORDER)
                .append("\n")
                .append(VERTICAL_DOUBLE_LINE)
                .append(String.format(Locale.US, "Thread     : %s at %s ", Thread.currentThread().getName(), timeFormat.format(new Date())))
                .append("\n")
                .append(MIDDLE_BORDER)
                .append("\n")
                .append(VERTICAL_DOUBLE_LINE)
                .append(String.format(Locale.US, "methodName : %s", methodName))
                .append("\n")
                .append(MIDDLE_BORDER)
                .append("\n")
                .append(VERTICAL_DOUBLE_LINE)
                .append("params     :(");
        for (int i = 0; i < parameterValues.length; i++) {
            if(i == 0){
                logBuilder.append("\n")
                        .append(VERTICAL_DOUBLE_LINE);
            }
            logBuilder
                    .append(String.format(Locale.US, "\t\t\t\t %s : %s ", parameterNames[i], StringUtils.toString(parameterValues[i])))
                    .append("\n")
                    .append(VERTICAL_DOUBLE_LINE);
        }
        logBuilder.append("            )")
                .append("\n")
                .append(MIDDLE_BORDER)
                .append("\n")
                .append(VERTICAL_DOUBLE_LINE);

        long startNanos = System.nanoTime();
        Object result = joinPoint.proceed();
        long stopNanos = System.nanoTime();

        long lengthMillis = TimeUnit.NANOSECONDS.toMillis(stopNanos - startNanos);
        boolean hasReturnType = signature instanceof MethodSignature
                && ((MethodSignature) signature).getReturnType() != void.class;
        logBuilder.append(String.format(Locale.US,"return     : %s",hasReturnType?StringUtils.toString(result):"void"))
                .append("\n")
                .append(MIDDLE_BORDER)
                .append("\n")
                .append(VERTICAL_DOUBLE_LINE)
                .append(String.format(Locale.US,"take time  : [%d]ms",lengthMillis))
                .append("\n")
                .append(BOTTOM_BORDER);

        Log.println(Log.INFO,"Tracking",logBuilder.toString());

        return result;
    }





}
