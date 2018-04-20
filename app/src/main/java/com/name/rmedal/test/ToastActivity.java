package com.name.rmedal.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.modelbean.FunctionBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.veni.rxtools.base.RxActivityOptionsTool;
import com.veni.rxtools.irecyclerview.adapter.CommonRecycleViewAdapter;
import com.veni.rxtools.view.LabelsView;
import com.veni.rxtools.view.RxTitle;
import com.veni.rxtools.view.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 */

public class ToastActivity extends BaseActivity {

    @BindView(R.id.toast_rxtitle)
    RxTitle toastTitle;
    @BindView(R.id.toast_labels)
    LabelsView toastLabels;
    @BindView(R.id.toast_refreshlayout)
    SmartRefreshLayout toastRefreshlayout;
    @BindView(R.id.toast_recyclerview)
    RecyclerView toastRecyclerview;

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new RxActivityOptionsTool().setContext(context)
                .setClass(ToastActivity.class)
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

    private CommonRecycleViewAdapter<FunctionBean> functionadapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        toastTitle.setLeftFinish(context);
        toastTitle.setTitle("Toast_LabelsView");
        setSwipeBackLayout(0);
        toastRefreshlayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                clooserefreshlayout();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                clooserefreshlayout();
            }
        });
        toastRefreshlayout.setRefreshHeader(new ClassicsHeader(context));

        labellist = new ArrayList<>();
        labellist.add("系统提示");
        labellist.add("普通提示");
        labellist.add("错误提示");
        labellist.add("警告提示");
        labellist.add("成功提示");
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
            case "系统提示":
                RxToast.showToast("showToast");
                break;
            case "普通提示":
                RxToast.normal("这是一个普通提示的Toast");
                break;
            case "信息提示":
                RxToast.info("这是一个信息提示的Toast");
                break;
            case "错误提示":
                RxToast.error("这是一个错误提示的Toast");
                break;
            case "警告提示":
                RxToast.warning("这是一个警告提示的Toast");
                break;
            case "成功提示":
                RxToast.success("这是一个成功提示的Toast");
                break;
        }

    }

    private void clooserefreshlayout() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        toastRefreshlayout.finishRefresh();
                        toastRefreshlayout.finishLoadmore();
                    }
                });
    }
}
