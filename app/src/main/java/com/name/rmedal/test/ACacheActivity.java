package com.name.rmedal.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.modelbean.FunctionBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.veni.rxtools.RxACache;
import com.veni.rxtools.base.RxActivityOptionsTool;
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

public class ACacheActivity extends BaseActivity {

    @BindView(R.id.toast_rxtitle)
    RxTitle toastTitle;
    @BindView(R.id.toast_refreshlayout)
    SmartRefreshLayout toastRefreshlayout;
    @BindView(R.id.toast_recyclerview)
    RecyclerView toastRecyclerview;

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new RxActivityOptionsTool().setContext(context)
                .setClass(ACacheActivity.class)
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

    private BaseQuickAdapter<FunctionBean, BaseViewHolder> functionadapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        toastTitle.setLeftFinish(context);
        toastTitle.setTitle("时效存储");
        setSwipeBackLayout(0);
        toastRefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                clooserefreshlayout();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                clooserefreshlayout();
            }
        });

        toastRefreshlayout.setRefreshHeader(new ClassicsHeader(context));

        functionadapter = new BaseQuickAdapter<FunctionBean, BaseViewHolder>(R.layout.activity_toast_spink) {
            @Override
            protected void convert(BaseViewHolder viewHolder, FunctionBean item) {
                viewHolder.setVisible(R.id.spink_item, false)
                        .setText(R.id.spink_tv, item.getFunctionName());
            }
        };

        functionadapter.addHeaderView(upHeaderView());
        functionadapter.openLoadAnimation();
        toastRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        toastRecyclerview.setAdapter(functionadapter);

        replaceadapter("请插入或读取数据");
    }

    private void replaceadapter(String tipstr) {
        List<FunctionBean> functionlist = new ArrayList<>();
        functionlist.add(new FunctionBean(tipstr, 0, null));
        functionadapter.replaceData(functionlist);
    }

    private View upHeaderView() {

        //添加Header
        View header = LayoutInflater.from(this).inflate(R.layout.activity_toast_lables, null, false);

        LabelsView toastLabels = header.findViewById(R.id.toast_labels);
        labellist = new ArrayList<>();
        labellist.add("插入数据");
        labellist.add("读取插入数据");
        toastLabels.setLabels(labellist); //直接设置一个字符串数组就可以了。

        toastLabels.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            public void onLabelClick(View label, String labelText, int position) {
                //label是被点击的标签，labelText是标签的文字，position是标签的位置。
                String labelstr = labellist.get(position);
                setfuctionview(labelstr);
            }
        });
        return header;
    }

    private void setfuctionview(String labelstr) {
        switch (labelstr) {
            case "插入数据": {
                RxACache.get(context).put("数据", "-----", RxACache.TIME_MINUTE);
                long time = RxACache.get(context).getAsTime("数据");
                replaceadapter("插入数据\nkey:数据\nvalue:-----\n剩余时间:" + time);
                break;
            }
            case "读取插入数据": {
                String value = RxACache.get(context).getAsString("数据");
                long time = RxACache.get(context).getAsTime("数据");
                replaceadapter("读取数据\nkey:数据\nvalue:" + value + "\n剩余时间:" + time);
                break;
            }
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
                        toastRefreshlayout.finishLoadMore();
                        replaceadapter("请插入或读取数据");
                    }
                });
    }
}
