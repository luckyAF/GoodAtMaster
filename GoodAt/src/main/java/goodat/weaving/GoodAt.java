package goodat.weaving;

/**
 * 类描述：
 *
 * @author Created by luckyAF on 2019-03-21
 */
public final class GoodAt {

    private static Interceptor mInterceptor = null;
    private static PermissionApply mPermissionApply = null;
    /**
     * 上一次调用的时间
     */
    private static long sLastCllTime;

    /**
     * 最近一次调用的方法
     */
    private static String sLastCallMethod;





    public static void  setInterceptor(Interceptor interceptor){
        mInterceptor = interceptor;
    }

    public static void setPermissionApply(PermissionApply permissionApply){
        mPermissionApply = permissionApply;
    }

     static boolean intercept(int type){
        if(null != mInterceptor){
            return mInterceptor.handle(type);
        }
        return false;
    }



    /**
     * 是否是 短时间多次调用
     *
     * @param intervalMillis 时间间期（毫秒）
     * @return true:是，false:不是
     */
     static boolean isFastDoubleCall(String methodName,long intervalMillis) {
        long time = System.currentTimeMillis();
        long interval = time - sLastCllTime;
        if (0 < interval && interval < intervalMillis && methodName.equals(sLastCallMethod)) {
            return true;
        } else {
            sLastCllTime = time;
            sLastCallMethod = methodName;
            return false;
        }
    }


    protected static void requestPermission(String[] permissions, ApplyCallback applyCallback){
        if(null != mPermissionApply){
            mPermissionApply.request(permissions, applyCallback);
        }
    }


    public interface Interceptor{
        /**
         * 处理 type
         * @param type 类型
         * @return 是否拦截
         */
        boolean handle(int type);
    }



    public interface PermissionApply{
        /**
         * 请求权限
         * @param permissions 权限列表
         * @param callback  回调
         */
        void request(String[] permissions, ApplyCallback callback);
    }


    /**
     * onGranted 和 onDenied 只会调一个
     */
    public interface ApplyCallback {
        /**
         * 授予权限
         */
        void onGranted();

    }


}
