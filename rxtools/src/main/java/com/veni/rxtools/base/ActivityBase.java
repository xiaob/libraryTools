package com.veni.rxtools.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.veni.rxtools.RxActivityTool;
import com.veni.rxtools.RxBarTool;

public class ActivityBase extends AppCompatActivity {
    public ActivityBase context;
    private AlertDialogBuilder dialogBuilder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // 把actvity放到application栈中管理
        RxActivityTool.getActivityTool().addActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyDialogBuilder();
        RxActivityTool.getActivityTool().finishActivity(this);
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor(int color) {
        RxBarTool.setStatusBarColor(this, color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     * 设置透明状态栏
     * 增加 最顶部的子标签 高度
     * DisplayUtil.ViewinitState(view);
     * 最顶部的子标签 必须设置 android:fitsSystemWindows="true" 属性
     * 否则标题栏会被导航栏遮挡
     */
    protected void SetTranslanteBar(View view) {
        RxBarTool.translucentStatusBar(this);
        if (view != null) {
            RxBarTool.ViewinitState(view);
        }
//        context.getWindow().addContentView();添加虚拟按键的高度
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    /**
     * 获取默认Dialog
     */
    public AlertDialogBuilder creatDialogBuilder() {
        destroyDialogBuilder();
        dialogBuilder = new AlertDialogBuilder(context);
        return dialogBuilder;
    }

    public void destroyDialogBuilder() {
        if (dialogBuilder != null) {
            dialogBuilder.dismissDialog();
            dialogBuilder = null;
        }
    }
}
