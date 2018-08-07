package com.name.rmedal.base;

import android.app.Activity;
import android.content.Context;

import com.name.rmedal.R;
import com.veni.tools.LogTools;
import com.veni.tools.base.AlertDialogBuilder;

public class HttpTipLoadDialog {

    private volatile static HttpTipLoadDialog instance;
    private AlertDialogBuilder dialogBuilder = null;

    private Context context;

    /**
     * 单一实例
     */
    public static HttpTipLoadDialog getHttpTipLoadDialog() {
        if (instance == null) {
            synchronized (HttpTipLoadDialog.class) {
                if (instance == null) {
                    instance = new HttpTipLoadDialog();
                }
            }
        }
        return instance;
    }
    /**
     * showDialog & dismissDialog 在http 请求开始的时候显示，结束的时候消失
     * 当然不是必须需要显示的 !
     */
    public void showDialog(Context context,final String messageText) {
        this.context = context;
        if (context == null || !(context instanceof Activity) || ((Activity) context).isFinishing())
            return;
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                creatDialogBuilder().setDialog_message(messageText)
                        .setLoadingView(R.color.colorAccent)
                        .builder().show();
            }
        });
        LogTools.e("HttpTipLoadDialog",  "showDialog");
    }

    public void dismissDialog() {
        if (context == null || !(context instanceof Activity))
            return;             //maybe not good !
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                destroyDialogBuilder();
            }
        });
        LogTools.e("HttpTipLoadDialog",  "dismissDialog");
    }
    /**
     * 获取默认Dialog
     */
    private AlertDialogBuilder creatDialogBuilder() {
        destroyDialogBuilder();
        dialogBuilder = new AlertDialogBuilder(context);
        return dialogBuilder;
    }

    private void destroyDialogBuilder() {
        if (dialogBuilder != null) {
            dialogBuilder.dismissDialog();
            dialogBuilder = null;
        }
    }
}
