package com.name.rmedal.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.name.rmedal.R;
import com.name.rmedal.base.BaseFragment;
import com.name.rmedal.bigimage.BigImagePagerActivity;
import com.name.rmedal.modelbean.BigImageBean;
import com.name.rmedal.modelbean.FunctionBean;
import com.name.rmedal.test.ACacheActivity;
import com.name.rmedal.test.Dialog_ProgressActivity;
import com.name.rmedal.test.ToastActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.veni.rxtools.RxJsonTools;
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

    private BaseQuickAdapter<FunctionBean, BaseViewHolder> functionadapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        homeRxtitle.setLeftIconVisibility(false);
        homeRefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
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
                viewHolder.setOnClickListener(R.id.function_item_ll, item.getNoFastClickListener());
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
        functionlist.add(new FunctionBean("查看大图", 0, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                List<BigImageBean> img_list = new ArrayList<>();
                img_list.add(new BigImageBean("http://a0.att.hudong.com/31/35/300533991095135084358827466.jpg"
                        ,"美女111"));
                img_list.add(new BigImageBean("http://www.zhlzw.com/UploadFiles/Article_UploadFiles/201210/2012102917591370.jpg"
                        ,"美女222"));
                img_list.add(new BigImageBean("http://a3.topitme.com/1/21/79/1128833621e7779211o.jpg"
                        ,"美女333"));
                String imglistjson = RxJsonTools.toJson(img_list);
                BigImagePagerActivity.startAction(context, imglistjson, 0);
            }
        }));
        functionlist.add(new FunctionBean("数据时效存储", 0, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                ACacheActivity.startAction(context);
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
                        homeRefreshlayout.finishLoadMore();
                    }
                });
    }
}

