package com.veni.tools;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import java.util.List;
import java.util.Stack;


/**
 * Created by xiyn on 2016/1/24.
 * 封装Activity操作相关工具类
 * addActivity                 : 添加Activity 到栈
 * currentActivity             : 获取当前的Activity（堆栈中最后一个压入的)
 * preActivity             : 获取当前Activity的前一个Activity
 * finishActivity              : 结束当前Activity（堆栈中最后一个压入的）
 * finishActivity(acr)         : 结束指定的Activity
 * removeActivity              : 移除指定的Activity
 * finishActivity (cls)        : 结束指定类名的Activity
 * finishAllActivity           : 结束所有的Activity
 * getActivityByClassName      : 通过类名获取Activity
 * returnToActivity           : 返回到指定类名的activity
 * isOpenActivity             : 是否已经打开指定的activity
 * AppExit                     : 退出当前APP
 *
 * 单个Activity操作
 * isExistActivity             : 判断是否存在指定Activity
 * launchActivity              : 打开指定的Activity
 * getLauncherActivity         : 获取launcher activity
 */
public class ActivityTools {
    private  Stack<Activity> activityStack;
    private volatile static ActivityTools instance;

    private ActivityTools() {

    }

    /**
     * 单一实例
     */
    public static ActivityTools getActivityTool() {
        if (instance == null) {
            synchronized (ActivityTools.class) {
                if (instance == null) {
                    instance = new ActivityTools();
                    instance.activityStack = new Stack();
                }
            }

        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity的前一个Activity
     */
    public Activity preActivity() {
        int index = activityStack.size() - 2;
        if (index < 0) {
            return null;
        }
        Activity activity = activityStack.get(index);
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().getCanonicalName().equals(cls.getCanonicalName())) {
                    finishActivity(activity);
                }
            }
        } catch (Exception ignored) {
        }
    }

    public Activity getActivityByClassName(Class<?> cls) {
        Activity targetactivity = null;
        try {
            for (Activity activity : activityStack) {
                if (activity.getClass().getCanonicalName().equals(cls.getCanonicalName())) {
                    targetactivity = activity;
                }
            }
        } catch (Exception ignored) {
        }
        return targetactivity;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 返回到指定的activity
     *
     * @param cls
     */
    public void returnToActivity(Class<?> cls) {
        while (activityStack.size() != 0)
            if (activityStack.peek().getClass() == cls) {
                break;
            } else {
                finishActivity(activityStack.peek());
            }
    }


    /**
     * 是否已经打开指定的activity
     *
     * @param cls
     * @return
     */
    public boolean isOpenActivity(Class<?> cls) {
        if (activityStack != null) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (cls == activityStack.peek().getClass()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 退出应用程序
     *
     * @param context      上下文
     * @param isBackground 是否开开启后台运行
     */
    public void AppExit(Context context, Boolean isBackground) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            if (activityMgr != null) {
                activityMgr.restartPackage(context.getPackageName());
            }
        } catch (Exception ignored) {
        } finally {
            // 注意，如果您有后台程序运行，请不要支持此句子
            if (!isBackground) {
                System.exit(0);
            }
        }
    }

    /**
     * 判断是否存在指定Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public  boolean isExistActivity(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(context.getPackageManager()) == null ||
                context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 打开指定的Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     */
    public  void launchActivity(Context context, String packageName, String className) {
        launchActivity(context, packageName, className, null);
    }

    /**
     * 打开指定的Activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     */
    public  void launchActivity(Context context, String packageName, String className, Bundle bundle) {
        context.startActivity(IntentTools.getComponentNameIntent(packageName, className, bundle));
    }

    /**
     * 获取launcher activity
     *
     * @param context     上下文
     * @param packageName 包名
     * @return launcher activity
     */
    public  String getLauncherActivity(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo info : infos) {
            if (info.activityInfo.packageName.equals(packageName)) {
                return info.activityInfo.name;
            }
        }
        return "no " + packageName;
    }

}
