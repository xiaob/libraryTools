package com.name.rmedal.ui.main;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.name.rmedal.R;
import com.name.rmedal.base.BaseFragment;
import com.name.rmedal.modelbean.FunctionBean;
import com.name.rmedal.test.Dialog_ProgressActivity;
import com.name.rmedal.test.ToastActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.veni.rxtools.RxLogTool;
import com.veni.rxtools.RxTool;
import com.veni.rxtools.interfaces.OnNoFastClickListener;
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

    private List<FunctionBean> functionlist;
    private BaseQuickAdapter<FunctionBean, BaseViewHolder> functionadapter;

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

        functionadapter = new BaseQuickAdapter<FunctionBean, BaseViewHolder>(R.layout.fragment_function_item) {

            @Override
            protected void convert(BaseViewHolder viewHolder, FunctionBean item) {
                ImageView function_item_iv = viewHolder.getView(R.id.function_item_iv);

                viewHolder.setText(R.id.function_item_tv, item.getFunctionName());
                viewHolder.setOnClickListener(R.id.function_item_ll,item.getNoFastClickListener());
                int image = item.getFunctionImage();
                if (image != 0) {
                    function_item_iv.setVisibility(View.VISIBLE);
                    function_item_iv.setImageDrawable(ContextCompat.getDrawable(RxTool.getContext(), image));
                }
            }
        };
        functionadapter.openLoadAnimation();
        homeFunctions.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        homeFunctions.setAdapter(functionadapter);
        setfuctionview();
    }

    private void setfuctionview() {
        functionlist = new ArrayList<>();
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
        functionadapter.replaceData(functionlist);
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

