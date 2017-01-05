package cn.wycode.bike;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

import cn.wycode.bike.activity.HomeActivity;


/**
 * Created by Alex on 2015/10/15.
 * 应用程序Activity管理类：管理Activity和应用程序
 */
public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
    }

    /**
     * 获取单例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * @return activity
     */
    public Stack<Activity> getActivities() {
        return activityStack;
    }

    /**
     * 获取顶层Activity
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中后一个压入的Activity)
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束除开指定Activity的其他Activity
     */
    public void finishOtherActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (!activityStack.get(i).getClass().equals(cls)) {
                this.finishActivity(activityStack.get(i));
                //如果不加这一行，remove之后当前元素的下标跟变化之后的size完全不对应，可能会跳过几个元素没有检查或者下标会大于size造成数组越界
                i = -1;
            }
        }
    }

    /**
     * 结束除开指定Activity:HomeActivity的其他Activity
     */
    public void finishOtherHomeActivity(Class<?> cls) {

        for (int i = 0; i < activityStack.size(); i++) {
            if (!activityStack.get(i).getClass().equals(cls)
                    && !activityStack.get(i).getClass().equals(HomeActivity.class)) {
                this.finishActivity(activityStack.get(i));
                i = -1;
            }
        }

    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                this.finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        //移出Stack中所有的元素
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            this.finishAllActivity();
//            MobclickAgent.onKillProcess(context);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
