package cn.wycode.bike.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by wangyu on 2015/10/12.
 * Toast显示时间根据文字长短确定
 * 提供多次提示只显示一次的方案
 * 提供带图片并居中显示的Toast
 */
public class ToastUtil {

    private static final int textLengthSign = 6;

    /**
     * 居中带图片的toast
     *
     * @param context     上下文
     * @param msg         消息
     * @param drawableRes 图片id
     */
    public static void showToastByPic(Context context, String msg, @DrawableRes int drawableRes) {
        Toast toast = makeText(context, msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(drawableRes);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    /**
     * 默认
     * @param context
     * @param msg
     */
    public static void showToastDefault(Context context, String msg) {
        Toast toast = makeText(context, msg);
        toast.show();
    }

    private static long lastShowTime = 0;
    private static String lastShowMessage = null;

    /**
     * 显示Toast，多个同样消息的Toast只显示一次
     *
     * @param context 上下文
     * @param msg     消息
     */
    public static void showToastOnce(Context context, String msg) {
        Toast toast;
        if (msg.equals(lastShowMessage)) {
            if (System.currentTimeMillis() - lastShowTime > 3000) {
                toast = makeText(context, msg);
                toast.show();
                lastShowTime = System.currentTimeMillis();
            }
        } else {
            showToastDefault(context, msg);
            lastShowMessage = msg;
            lastShowTime = System.currentTimeMillis();
        }
    }

    /**
     * 显示Toast，多个同样消息的Toast只显示一次
     *
     * @param context   上下文
     * @param stringRes 消息id
     */
    public static void showToastOnce(Context context, @StringRes int stringRes) {
        showToastOnce(context, context.getString(stringRes));
    }

    private static Toast makeText(Context context, String msg) {
        if (msg.isEmpty()) {
            return Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else if (msg.length() > textLengthSign) {
            return Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            return Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        }
    }

}
