package com.veni.rxtools.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.StyleRes;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.veni.rxtools.R;
import com.veni.rxtools.RxDeviceTool;
import com.veni.rxtools.RxTool;
import com.veni.rxtools.interfaces.OnDelayListener;
import com.veni.rxtools.view.progressing.sprite.SpriteContainer;
import com.veni.rxtools.view.progressing.style.ThreeBounce;

/**
 * 作者：xiyn on 2017/12/04 10:36
 * 当前类注释:
 */

public class AlertDialogBuilder {

    private Context context;

    private Dialog dialog;

    private int themeResId = R.style.NormalDialogStyle;//style

    private boolean cancelable = false;//点击边缘是否可以消失

    private boolean baseviewisloading = false;//true 显示 加载dialog

    private String dialog_title = "";//标题

    private String dialog_message = "";//信息

    private Drawable messageDrawable;//drawable

    private int drawableseat = 1;//drawable 位置

    private long canceltime = 0;//自动消失的时间

    private String dialog_leftstring = "";//左边文字

    private int leftcolor = R.color.dodgerblue;//颜色

    private View.OnClickListener leftlistener = null;//监听

    private String dialog_rightstring = "";//右边文字

    private int rightcolor = R.color.gray;//颜色

    private View.OnClickListener rightlistener = null;//监听

    /**
     * 上下文
     */
    public AlertDialogBuilder(Context context) {
        this.context = context;
    }

    /**
     * 弹窗
     */
    private AlertDialogBuilder setDialog(Dialog dialog) {
        this.dialog = dialog;
        return this;
    }

    /**
     * 主题
     */
    public AlertDialogBuilder setThemeResId(@StyleRes int themeResId) {
        if (themeResId != 0) {
            this.themeResId = themeResId;
        }
        return this;
    }

    /**
     * 点击边缘是否消失
     */
    public AlertDialogBuilder setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
        return this;
    }

    /**
     * 自动消失的时间
     */
    public AlertDialogBuilder setCanceltime(long canceltime) {
        if (canceltime != 0) {
            this.canceltime = canceltime;
        }
        return this;
    }
    /**
     * 标题
     */
    public AlertDialogBuilder setDialog_title(String dialog_title) {
        this.dialog_title = dialog_title;
        return this;
    }

    /**
     * 消息内容
     */
    public AlertDialogBuilder setDialog_message(String dialog_message) {
        this.dialog_message = dialog_message;
        return this;
    }

    /**
     * 子页面
     */
    private AlertDialogBuilder setMessageView(Drawable messageDrawable) {
        this.messageDrawable = messageDrawable;
        return this;
    }

    /**
     * 子页面位置
     * 0 left, 1 top,2 right,3 bottom
     */
    public AlertDialogBuilder setDrawableseat(int drawableseat) {
        this.drawableseat = drawableseat;
        return this;
    }

    public Drawable getMessageView() {
        return messageDrawable;
    }

    /**
     * 左边的文字
     */
    public AlertDialogBuilder setDialog_leftstring(String dialog_leftstring) {
        if (dialog_leftstring != null && !dialog_leftstring.equals("")) {
            this.dialog_leftstring = dialog_leftstring;
        }
        return this;
    }

    /**
     * 左边的文字颜色
     */
    public AlertDialogBuilder setLeftcolor(@ColorInt int leftcolor) {
        if (leftcolor != 0) {
            this.leftcolor = leftcolor;
        }
        return this;
    }

    /**
     * 左边的文字点击事件
     */
    public AlertDialogBuilder setLeftlistener(View.OnClickListener leftlistener) {
        if (leftlistener != null) {
            this.leftlistener = leftlistener;
        }
        return this;
    }

    /**
     * 右边的文字
     */
    public AlertDialogBuilder setDialog_rightstring(String dialog_rightstring) {
        if (dialog_rightstring != null && !dialog_rightstring.equals("")) {
            this.dialog_rightstring = dialog_rightstring;
        }
        return this;
    }

    /**
     * 右边的文字颜色
     */
    public AlertDialogBuilder setRightcolor(@ColorInt int rightcolor) {
        if (rightcolor != 0) {
            this.rightcolor = rightcolor;
        }
        return this;
    }

    /**
     * 右边的文字点击事件
     */
    public AlertDialogBuilder setRightlistener(View.OnClickListener rightlistener) {
        if (rightlistener != null) {
            this.rightlistener = rightlistener;
        }
        return this;
    }

    /**
     * 加载对话框
     */
    public AlertDialogBuilder setLoadingView(int color) {
        if (context != null) {
//            Circle threeBounce = new Circle();
            ThreeBounce threeBounce = new ThreeBounce();
            threeBounce.setBounds( 0, 24, 100, 100);//四个参数指的是drawable将在被绘制在canvas的哪个矩形区域内。
            threeBounce.setColor(context.getResources().getColor(color));
            baseviewisloading = true;
            setMessageView(threeBounce);
        }
        return this;
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
            if (messageDrawable != null && messageDrawable instanceof SpriteContainer) {
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (messageDrawable != null && messageDrawable instanceof SpriteContainer) {
                            ((SpriteContainer) messageDrawable).stop();
                        }
                    }
                });
                ((SpriteContainer) messageDrawable).start();
            }
            if(canceltime!=0){
                RxTool.delayToDo(canceltime, new OnDelayListener() {
                    @Override
                    public void doSomething() {
                        dismissDialog();
                    }
                });
            }
        }
    }

    public void dismissDialog() {
        if (context == null || isDestroyedCompatible()) {
            return;
        }
        if (dialog != null) {
            dialog.cancel();
            if (messageDrawable != null && messageDrawable instanceof SpriteContainer) {
                ((SpriteContainer) messageDrawable).stop();
            }
        }
        dialog = null;
        context = null;
    }

    /**
     * 判断页面是否已经被销毁（异步回调时使用）
     */
    public boolean isDestroyedCompatible() {
        if (Build.VERSION.SDK_INT >= 17) {
            return isDestroyedCompatible17();
        } else {
            return ((Activity) context).isFinishing();
        }
    }

    @TargetApi(17)
    private boolean isDestroyedCompatible17() {
        return ((Activity) context).isDestroyed();
    }

    public AlertDialogBuilder builder() {
        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = new Dialog(context, themeResId);
        View mDialogView;
        if (baseviewisloading) {
            mDialogView = getLoading_Dialog();
        } else {
            mDialogView = getDefault_Dialog();
            int width = RxDeviceTool.getScreenWidth(context);
            setDialogSeat_Width((int) (width * 0.80));
        }
        dialog.setContentView(mDialogView);
//        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);

        if (baseviewisloading) {
            setDialogSeat_Gravity(Gravity.CENTER);
        } else {
            int width = RxDeviceTool.getScreenWidth(context);
            setDialogSeat_Width((int) (width * 0.80));
        }
        setDialog(dialog);
        return this;
    }

    private View getDefault_Dialog() {
        View mDialogView = View.inflate(context, R.layout.dialog_default, null);

        TextView dialogTitle = (TextView) mDialogView.findViewById(R.id.dialog_title);
        TextView dialogMessageTv = (TextView) mDialogView.findViewById(R.id.dialog_message_tv);
        TextView dialogLeftbtn = (TextView) mDialogView.findViewById(R.id.dialog_leftbtn);
        TextView dialogLineV = (TextView) mDialogView.findViewById(R.id.dialog_line_v);
        TextView dialogLineH = (TextView) mDialogView.findViewById(R.id.dialog_line_h);
        TextView dialogRightbtn = (TextView) mDialogView.findViewById(R.id.dialog_rightbtn);

        if (dialog_title != null && !dialog_title.equals("")) {
            dialogTitle.setVisibility(View.VISIBLE);
            dialogTitle.setText(dialog_title);
        }
        if (messageDrawable != null) {
            dialogMessageTv.setCompoundDrawables(drawableseat == 0 ? messageDrawable : null
                    , drawableseat == 1 ? messageDrawable : null
                    , drawableseat == 2 ? messageDrawable : null
                    , drawableseat == 3 ? messageDrawable : null);
        }
        dialogMessageTv.setText(dialog_message);
        if (dialog_leftstring != null && !dialog_leftstring.equals("")) {
            dialogLeftbtn.setVisibility(View.VISIBLE);
            dialogLeftbtn.setText(dialog_leftstring);
            dialogLeftbtn.setTextColor(ContextCompat.getColor(context, leftcolor));
            dialogLeftbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (leftlistener != null) {
                        leftlistener.onClick(view);
                    }
                }
            });
        }
        if (dialog_rightstring != null && !dialog_rightstring.equals("")) {
            dialogRightbtn.setVisibility(View.VISIBLE);
            dialogRightbtn.setText(dialog_rightstring);
            dialogRightbtn.setTextColor(ContextCompat.getColor(context, rightcolor));
            dialogRightbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    if (rightlistener != null) {
                        rightlistener.onClick(view);
                    }
                }
            });
        }
        if (dialogLeftbtn.getVisibility() == View.VISIBLE
                && dialogRightbtn.getVisibility() == View.VISIBLE) {
            dialogLineV.setVisibility(View.VISIBLE);
        } else if (dialogLeftbtn.getVisibility() == View.GONE
                && dialogRightbtn.getVisibility() == View.GONE) {
            dialogLineH.setVisibility(View.GONE);
        }
        return mDialogView;
    }

    private View getLoading_Dialog() {
        View loadingdialog = View.inflate(context, R.layout.dialog_loading, null);
        TextView loading_tv = (TextView) loadingdialog.findViewById(R.id.loading_tv);
        if (messageDrawable != null) {
            loading_tv.setCompoundDrawables(drawableseat == 0 ? messageDrawable : null
                    , drawableseat == 1 ? messageDrawable : null
                    , drawableseat == 2 ? messageDrawable : null
                    , drawableseat == 3 ? messageDrawable : null);
        }
        loading_tv.setText(dialog_message);
        return loadingdialog;
    }

    private AlertDialogBuilder setDialogSeat_Gravity(int gravity) {
        setDialogSeat(gravity, -234, -234);
        return this;
    }

    private AlertDialogBuilder setDialogSeat_Width(int width) {
        setDialogSeat(Gravity.CENTER, width, -234);
        return this;
    }

    /**
     * 设置dialog位置 ViewGroup.LayoutParams.MATCH_PARENT
     * int width = RxDeviceTool.getScreenWidth(context);
     * (int) (width * 0.80)
     *
     * @param gravity Gravity.CENTER
     * @return
     */
    private AlertDialogBuilder setDialogSeat(int gravity, int width, int height) {
        if (dialog != null) {
            Window dialogWindow = dialog.getWindow();
            if (dialogWindow != null) {
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                if (width != -234) {
                    lp.width = width;
                }
                if (height != -234) {
                    lp.height = height;
                }
                dialogWindow.setAttributes(lp);
                dialogWindow.setGravity(gravity);
            }
        }
        return this;
    }
}
