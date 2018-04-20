package com.name.rmedal.test;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.veni.rxtools.base.RxActivityOptionsTool;
import com.veni.rxtools.view.LabelsView;
import com.veni.rxtools.view.RxTitle;
import com.veni.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Dialog_PopActivity extends BaseActivity {

    @BindView(R.id.toast_rxtitle)
    RxTitle toastTitle;
    @BindView(R.id.toast_labels)
    LabelsView toastLabels;

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new RxActivityOptionsTool().setContext(context)
                .setClass(Dialog_PopActivity.class)
                .customAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_toast;
    }

    @Override
    public void initPresenter() {

    }

    private List<String> labellist;

    @Override
    public void initView(Bundle savedInstanceState) {
        toastTitle.setLeftFinish(context);
        setSwipeBackLayout(0);

        labellist = new ArrayList<>();
        labellist.add("showToast");
        labellist.add("showToastShort");
        labellist.add("showToastLong");
        labellist.add("normalToast");
        labellist.add("errorToast");
        labellist.add("warningToast");
        labellist.add("successToast");
        toastLabels.setLabels(labellist); //直接设置一个字符串数组就可以了。

        toastLabels.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            public void onLabelClick(View label, String labelText, int position) {
                //label是被点击的标签，labelText是标签的文字，position是标签的位置。
                String labelstr = labellist.get(position);
                setfuctionview(labelstr);
            }
        });
    }

    private void setfuctionview(String labelstr) {
        switch (labelstr) {
            case "showToast":
                RxToast.showToast("showToast");
                break;
            case "showToastShort":
                RxToast.showToastShort("ShortToast");
                break;
            case "showToastLong":
                RxToast.showToastLong("LongToast");
                break;
            case "normalToast":
                RxToast.normal("normal");
                break;
            case "errorToast":
                RxToast.error("error");
                break;
            case "warningToast":
                RxToast.warning("warning");
                break;
            case "successToast":
                RxToast.success("success");
                break;
        }

    }

}
