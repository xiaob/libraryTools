package com.veni.tools.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.veni.tools.ActivityTools;
import com.veni.tools.ToolBarUtils;

public class ActivityBase extends AppCompatActivity {
    public ActivityBase context;
    private AlertDialogBuilder dialogBuilder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // 把actvity放到application栈中管理
        ActivityTools.getActivityTool().addActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyDialogBuilder();
        ActivityTools.getActivityTool().finishActivity(this);
    }

    protected void onCreateCustomToolBar(Toolbar toolbarBaseTb) {
        onCreateCustomToolBar(toolbarBaseTb, true);
    }

    /**
     * @param homeAsUpEnabled 是否可点击
     */
    protected void onCreateCustomToolBar(Toolbar toolbarBaseTb, boolean homeAsUpEnabled) {
        ToolBarUtils.getToolBarUtils().onCreateCustomToolBar(context, toolbarBaseTb, homeAsUpEnabled);
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
