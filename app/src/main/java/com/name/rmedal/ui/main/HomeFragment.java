package com.name.rmedal.ui.main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.name.rmedal.R;
import com.name.rmedal.base.BaseFragment;
import com.name.rmedal.modelbean.FunctionBean;
import com.name.rmedal.test.Dialog_ProgressActivity;
import com.name.rmedal.test.ToastActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.veni.rxtools.RxTool;
import com.veni.rxtools.interfaces.OnNoFastClickListener;
import com.veni.rxtools.irecyclerview.adapter.CommonRecycleViewAdapter;
import com.veni.rxtools.irecyclerview.adapter.ViewHolderHelper;
import com.veni.rxtools.irecyclerview.animation.ScaleInAnimation;
import com.veni.rxtools.view.RxTitle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * 作者：kkan on 2018/2/24 14:41
 * 当前类注释:
 * 首页
 */

public class HomeFragment extends BaseFragment {


    @BindView(R.id.home_rxtitle)
    RxTitle homeRxtitle;
    @BindView(R.id.home_functions)
    RecyclerView homeFunctions;
    @BindView(R.id.home_refreshlayout)
    SmartRefreshLayout homeRefreshlayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {

    }

    private  CommonRecycleViewAdapter<FunctionBean> functionadapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        homeRxtitle.setLeftIconVisibility(false);
        homeRefreshlayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                clooserefreshlayout();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                clooserefreshlayout();
            }
        });
        homeRefreshlayout.setRefreshHeader(new ClassicsHeader(context));

        functionadapter = new CommonRecycleViewAdapter<FunctionBean>(context, R.layout.fragment_function_item) {
            @Override
            public void convert(ViewHolderHelper holder, FunctionBean functionBean) {
                ImageView function_item_iv = holder.getView(R.id.function_item_iv);
                TextView function_item_tv = holder.getView(R.id.function_item_tv);

                View convertView = holder.getConvertView();
                final String name = functionBean.getFunctionName();
                int image = functionBean.getFunctionImage();
                OnNoFastClickListener noFastClickListener =functionBean.getNoFastClickListener();
                function_item_tv.setText(name);
                if (image != 0) {
                    function_item_iv.setVisibility(View.VISIBLE);
                    function_item_iv.setImageDrawable(ContextCompat.getDrawable(RxTool.getContext(), image));
                }
                convertView.setOnClickListener(noFastClickListener);
            }
        };
        homeFunctions.setAdapter(functionadapter);
        functionadapter.openLoadAnimation(new ScaleInAnimation());
        homeFunctions.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        setfuctionview();
    }

    private void setfuctionview() {
        List<FunctionBean> functionlist = new ArrayList<>();
        functionlist.add(new FunctionBean("Dialog展示", 0, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Dialog_ProgressActivity.startAction(context);
            }
        }));
        functionlist.add(new FunctionBean("Toast_LabelsView", 0, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                ToastActivity.startAction(context);
            }
        }));
        functionadapter.replaceAll(functionlist);
    }

    private void clooserefreshlayout() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        homeRefreshlayout.finishRefresh();
                        homeRefreshlayout.finishLoadmore();
                    }
                });
    }
}

