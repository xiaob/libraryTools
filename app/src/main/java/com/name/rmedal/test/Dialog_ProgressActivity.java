package com.name.rmedal.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.modelbean.FunctionBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.veni.rxtools.RxTool;
import com.veni.rxtools.base.RxActivityOptionsTool;
import com.veni.rxtools.interfaces.OnNoFastClickListener;
import com.veni.rxtools.irecyclerview.adapter.CommonRecycleViewAdapter;
import com.veni.rxtools.irecyclerview.adapter.ViewHolderHelper;
import com.veni.rxtools.irecyclerview.animation.ScaleInAnimation;
import com.veni.rxtools.view.LabelsView;
import com.veni.rxtools.view.RxTitle;
import com.veni.rxtools.view.progressing.SpinKitView;
import com.veni.rxtools.view.progressing.SpriteFactory;
import com.veni.rxtools.view.progressing.Style;
import com.veni.rxtools.view.progressing.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class Dialog_ProgressActivity extends BaseActivity {

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
                .setClass(Dialog_ProgressActivity.class)
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

    int[] colors = new int[]{
            android.graphics.Color.parseColor("#D55400"),
            android.graphics.Color.parseColor("#2B3E51"),
            android.graphics.Color.parseColor("#00BD9C"),
            android.graphics.Color.parseColor("#227FBB"),
            android.graphics.Color.parseColor("#7F8C8D"),
            android.graphics.Color.parseColor("#FFCC5C"),
            android.graphics.Color.parseColor("#D55400"),
            android.graphics.Color.parseColor("#1AAF5D"),
    };
    private List<String> labellist;

    private CommonRecycleViewAdapter<FunctionBean> functionadapter;

    @Override
    public void initView(Bundle savedInstanceState) {
        toastTitle.setLeftFinish(context);
        toastTitle.setTitle("Dialog_Progress");
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
        labellist.add("Dialog");
        labellist.add("loadingL");
        labellist.add("loadingT");
        labellist.add("loadingR");
        labellist.add("loadingB");
        labellist.add("三点上下晃动");
        labellist.add("两点上下晃动");
        labellist.add("波浪");
        labellist.add("对角旋转正方体");
        labellist.add("点追逐");
        labellist.add("圆圈 菊花");
        labellist.add("正方体网格");
        labellist.add("衰退圆圈");
        labellist.add("折叠正方体");
        labellist.add("复杂脉冲");
        labellist.add("复杂环形脉冲");
        toastLabels.setLabels(labellist); //直接设置一个字符串数组就可以了。

        toastLabels.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            public void onLabelClick(View label, String labelText, int position) {
                //label是被点击的标签，labelText是标签的文字，position是标签的位置。
                setfuctionview(labelText);
            }
        });

        functionadapter = new CommonRecycleViewAdapter<FunctionBean>(context, R.layout.activity_toast_spink) {
            @Override
            public void convert(ViewHolderHelper holder, FunctionBean functionBean) {
                SpinKitView spinKitView = holder.getView(R.id.spink_item);
                TextView spink_tv = holder.getView(R.id.spink_tv);

                Sprite drawable = functionBean.getSprite();
                drawable.setColor(R.color.colorAccent);
                spinKitView.setIndeterminateDrawable(drawable);
                spink_tv.setText(functionBean.getFunctionName());

            }
        };

        toastRecyclerview.setHasFixedSize(true);
        toastRecyclerview.setNestedScrollingEnabled(false);
        toastRecyclerview.setAdapter(functionadapter);
        functionadapter.openLoadAnimation(new ScaleInAnimation());
        toastRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        List<FunctionBean> functionlist = new ArrayList<>();

        for(int i=0;i< Style.values().length;i++){
            Style style = Style.values()[i];
            Sprite drawable = SpriteFactory.create(style);
            functionlist.add(new FunctionBean(style.name(),drawable,null));
        }
        functionadapter.replaceAll(functionlist);
    }



    private void setfuctionview(String labelstr) {
        switch (labelstr) {
            case "Dialog":
                creatDialogBuilder().setDialog_title("title")
                        .setDialog_message("message")
                        .setDialog_Left("leftbtn")
                        .setDialog_Right("rightbtn")
                        .setCancelable(true)
                        .setLeftlistener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setRightlistener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).builder().show();
                break;
            case "loadingL":
                creatDialogBuilder()
                        .setDialog_message("loading..两秒消失")
                        .setLoadingView(R.color.colorAccent)
                        .setCanceltime(2000)
                        .setDrawableseat(0)
                        .builder().show();
                break;
            case "loadingT":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .setLoadingView(R.color.colorAccent)
                        .setCancelable(true)
                        .setDrawableseat(1)
                        .builder().show();
                break;
            case "loadingR":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .setLoadingView(R.color.colorAccent)
                        .setCancelable(true)
                        .setDrawableseat(2)
                        .builder().show();
                break;
            case "loadingB":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .setCancelable(true)
                        .setDrawableseat(3)
                        .setLoadingView(R.color.colorAccent)
                        .builder().show();
                break;
            case "三点上下晃动":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .getThreeBounce(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "两点上下晃动":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .getDoubleBounce(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "波浪":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .getWave(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "对角旋转正方体":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .getWanderingCubes(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "点追逐":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .geChasingDots(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "圆圈 菊花":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .geCircle(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "正方体网格":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .geCubeGrid(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "衰退圆圈":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .geFadingCircle(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "折叠正方体":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .geFoldingCube(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "复杂脉冲":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .geMultiplePulse(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
                break;
            case "复杂环形脉冲":
                creatDialogBuilder()
                        .setDialog_message("loading..")
                        .geMultiplePulseRing(R.color.colorAccent)
                        .setCancelable(true)
                        .setLoadingView()
                        .builder().show();
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
