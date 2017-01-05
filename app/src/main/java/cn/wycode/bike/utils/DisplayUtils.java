package cn.wycode.bike.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

public class DisplayUtils {


    /**
     * Andorid.util 包下的DisplayMetrics 类提供了一种关于显示的通用信息，如显示大小，分辨率和字体。
     *
     * @param context
     * @return DisplayMetrics对象
     */
    public static DisplayMetrics getMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;

    }

    /**
     * dp转pixel
     */
    public static int dp2px(float dp, Context context) {
        DisplayMetrics metrics = getMetrics(context);
        return (int) (dp * (metrics.densityDpi / 160f));
    }

    /**
     * pixel转dp
     */
    public static float px2dp(float px, Context context) {
        DisplayMetrics metrics = getMetrics(context);
        return px / (metrics.densityDpi / 160f);
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getMetrics(context);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getMetrics(context);
        return dm.heightPixels;
    }


}
