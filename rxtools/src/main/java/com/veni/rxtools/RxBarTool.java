package com.veni.rxtools;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.reflect.Method;

/**
 * Created by xiyn on 2016/1/24.
 * setTransparentStatusBar     : 设置透明状态栏(api大于19方可使用)
 * hideStatusBar               : 隐藏状态栏
 * noTitle                     : 隐藏Title
 * FLAG_FULLSCREEN             : 设置全屏
 * getStatusBarHeight          : 获取状态栏高度
 * isStatusBarExists           : 判断状态栏是否存在
 * getActionBarHeight          : 获取ActionBar高度
 * showNotificationBar         : 显示通知栏
 * hideNotificationBar         : 隐藏通知栏
 * invokePanels                : 反射唤醒通知栏
 */
public class RxBarTool {
    /**
     * 设置view布局
     */
    public static FrameLayout.LayoutParams viewLayoutParams(View view, int gravity, int hight) {
        return viewLayoutParams(view, gravity, hight, hight, hight, hight);
    }

    public static FrameLayout.LayoutParams viewLayoutParams(View view, int gravity, int hight, int width, int height) {
        return viewLayoutParams(view, gravity, hight, hight, hight, hight, width, height);
    }

    public static FrameLayout.LayoutParams viewLayoutParams(View view, int gravity, int left, int top, int right, int bottom) {
        return viewLayoutParams(view, gravity, left, top, right, bottom,
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    }

    public static FrameLayout.LayoutParams viewLayoutParams(View view, int gravity, int left, int top, int right, int bottom
            , int width, int height) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
        layoutParams.gravity = gravity;
        if (left != 0 || top != 0 || right != 0 || bottom != 0) {
            layoutParams.setMargins(RxImageTool.dip2px(left), RxImageTool.dip2px(top), RxImageTool.dip2px(right), RxImageTool.dip2px(bottom));
        }
        view.setLayoutParams(layoutParams);
        return layoutParams;
    }

    /**
     * 动态的设置隐藏布局的高度
     */
    public static void ViewinitState(View view) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //
            //获取到状态栏的高度
            int statusHeight = getStatusBarHeight(view.getContext());
            //动态的设置隐藏布局的高度
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = params.height + statusHeight;
            view.setLayoutParams(params);
        }
    }

    /**
     * 判断状态栏是否存在
     *
     * @param activity activity
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isStatusBarExists(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        return (params.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) != WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    /**
     * 获取ActionBar高度
     *
     * @param activity activity
     * @return ActionBar高度
     */
    public static int getActionBarHeight(Activity activity) {
        TypedValue tv = new TypedValue();
        if (activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data, activity.getResources().getDisplayMetrics());
        }
        return 0;
    }

    /**
     * 显示通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context        上下文
     * @param isSettingPanel {@code true}: 打开设置<br>{@code false}: 打开通知
     */
    public static void showNotificationBar(Context context, boolean isSettingPanel) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "expand"
                : (isSettingPanel ? "expandSettingsPanel" : "expandNotificationsPanel");
        invokePanels(context, methodName);
    }

    /**
     * 隐藏通知栏
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.EXPAND_STATUS_BAR"/>}</p>
     *
     * @param context 上下文
     */
    public static void hideNotificationBar(Context context) {
        String methodName = (Build.VERSION.SDK_INT <= 16) ? "collapse" : "collapsePanels";
        invokePanels(context, methodName);
    }

    /**
     * 反射唤醒通知栏
     *
     * @param context    上下文
     * @param methodName 方法名
     */
    private static void invokePanels(Context context, String methodName) {
        try {
            Object service = context.getSystemService("statusbar");
            Class<?> statusBarManager = Class.forName("android.app.StatusBarManager");
            Method expand = statusBarManager.getMethod(methodName);
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*隐藏导航栏*/
    private static final int FLAG_IMMERSIVE =
            View.SYSTEM_UI_FLAG_IMMERSIVE // 与SYSTEM_UI_FLAG_HIDE_NAVIGATION组合使用，没有设置的话在隐藏导航栏后将没有交互
//            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // 隐藏虚拟按键(导航栏)
            | View.SYSTEM_UI_FLAG_FULLSCREEN; // Activity全屏显示，且状态栏被隐藏覆盖掉

    public static void exit(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            addFlags(activity.getWindow().getDecorView(), FLAG_IMMERSIVE);
        }
    }

    public static void addFlags(View decorView, int flags) {
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | flags);
    }

    public static void enter(Activity activity) {
        if (Build.VERSION.SDK_INT >= 19) {
            clearFlags(activity.getWindow().getDecorView(), FLAG_IMMERSIVE);
        }
    }

    public static void clearFlags(View view, int flags) {
        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~flags);
        // & ~flags 清除view.getSystemUiVisibility()中的flags
    }


    //==============================================================================================以下设置状态栏相关
    /**
     * 需要在布局中加入
     * android:clipToPadding="true"
     * android:fitsSystemWindows="true"
     * 这两行属性
     */

    private static final int COLOR_TRANSLUCENT = Color.parseColor("#00000000");

    public static final int DEFAULT_COLOR_ALPHA = 112;

    /**
     * set statusBarColor
     *
     * @param statusColor color
     * @param alpha       0 - 255
     */
    public static void setStatusBarColor(Activity activity, int statusColor, int alpha) {
        setStatusBarColor(activity, calculateStatusBarColor(statusColor, alpha));
    }

    public static void setStatusBarColor(Activity activity, int statusColor) {
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //First translucent status bar.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //After LOLLIPOP not translucent status bar
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //Then call setStatusBarColor.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(statusColor);
                //set child View not fill the system window
                View mChildView = mContentView.getChildAt(0);
                if (mChildView != null) {
                    ViewCompat.setFitsSystemWindows(mChildView, true);
                }
            } else {
                ViewGroup mDecorView = (ViewGroup) window.getDecorView();
                if (mDecorView.getTag() != null && mDecorView.getTag() instanceof Boolean && (Boolean) mDecorView.getTag()) {
                    //if has add fake status bar view
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    View mStatusBarView = mDecorView.getChildAt(0);
                    if (mStatusBarView != null) {
                        mStatusBarView.setBackgroundColor(statusColor);
                    }
                } else {
                    int statusBarHeight = getStatusBarHeight(activity);
                    //add margin
                    View mContentChild = mContentView.getChildAt(0);
                    if (mContentChild != null) {
                        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                        ViewCompat.setFitsSystemWindows(mContentChild, false);
                        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
                        lp.topMargin += statusBarHeight;
                        mContentChild.setLayoutParams(lp);

                        //add fake status bar view
                        View mStatusBarView = new View(activity);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
                        layoutParams.gravity = Gravity.TOP;
                        mStatusBarView.setLayoutParams(layoutParams);
                        mStatusBarView.setBackgroundColor(statusColor);
                        mDecorView.addView(mStatusBarView, 0);
                        mDecorView.setTag(true);
                    }

                }
            }
        }
    }

    public static void translucentStatusBar(Activity activity) {
        translucentStatusBar(activity, false, 0);
    }

    public static void translucentStatusBar(Activity activity, int color) {
        translucentStatusBar(activity, false, color);
    }

    /**
     * change to full screen mode
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground, int color) {
        if (color == 0) {
            color = COLOR_TRANSLUCENT;
        }
        Window window = activity.getWindow();
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        //set child View not fill the system window
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
        }
        //判断api是否大于19 (5.X 系统)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight(activity);
            //首先设置状态栏透明.
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //大于21 只需要设置 LayoutParams.
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (hideStatusBarBackground) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.setStatusBarColor(color);
                } else {
                    window.setStatusBarColor(calculateStatusBarColor(color, DEFAULT_COLOR_ALPHA));
                }
                //必须requestapplyinsets，否则会在屏幕底部有空
                if (mChildView != null) {
                    ViewCompat.requestApplyInsets(mChildView);
                }

            } else {
                ViewGroup mDecorView = (ViewGroup) window.getDecorView();
                if (mDecorView.getTag() != null && mDecorView.getTag() instanceof Boolean && (Boolean) mDecorView.getTag()) {
                    mChildView = mDecorView.getChildAt(0);
                    //删除假状态栏视图
                    mContentView.removeView(mChildView);
                    mChildView = mContentView.getChildAt(0);
                    if (mChildView != null) {
                        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
                        //取消顶部距离
                        if (lp != null && lp.topMargin >= statusBarHeight) {
                            lp.topMargin -= statusBarHeight;
                            mChildView.setLayoutParams(lp);
                        }
                    }
                    mDecorView.setTag(false);
                }
            }
        }
    }

    /**
     * 获取虚拟按键的高度
     * Get navigation bar height
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }
    /**
     * 获取状态栏高度
     * Get status bar height
     *
     * @param context 上下文
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }

    //Get alpha color
    private static int calculateStatusBarColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }
}
