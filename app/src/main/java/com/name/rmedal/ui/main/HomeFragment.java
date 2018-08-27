package com.name.rmedal.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.name.rmedal.api.AppConstant;
import com.name.rmedal.base.BaseFragment;
import com.name.rmedal.bigimage.BigImagePagerActivity;
import com.name.rmedal.bigimage.BigImageBean;
import com.name.rmedal.modelbean.FunctionBean;
import com.name.rmedal.test.ACacheActivity;
import com.name.rmedal.test.CardGroupActivity;
import com.name.rmedal.test.Dialog_ProgressActivity;
import com.name.rmedal.test.HeartLikeViewActivity;
import com.name.rmedal.test.PhoneInfoActivity;
import com.name.rmedal.test.PopWinActivity;
import com.name.rmedal.test.RichTextActivity;
import com.name.rmedal.test.RunTextActivity;
import com.name.rmedal.test.VerifyCodeActivity;
import com.name.rmedal.test.ToastActivity;
import com.name.rmedal.ui.zxing.ScanerCodeActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.veni.tools.DataTools;
import com.veni.tools.JsonTools;
import com.veni.tools.FutileTool;
import com.veni.tools.LogTools;
import com.veni.tools.interfaces.OnNoFastClickListener;
import com.veni.tools.StatusBarTools;
import com.veni.tools.view.TitleView;
import com.veni.tools.view.ToastTool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * 作者：kkan on 2018/2/24 14:41
 * 当前类注释:
 * 首页
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_rxtitle)
    TitleView homeRxtitle;
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
        StatusBarTools.setPaddingSmart(context, homeRxtitle);
        homeRefreshlayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                clooserefreshlayout();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
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
                    function_item_iv.setImageDrawable(ContextCompat.getDrawable(FutileTool.getContext(), image));
                }
            }
        };
        functionadapter.openLoadAnimation();
        homeFunctions.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        homeFunctions.setAdapter(functionadapter);
        setfuctionview();
    }
    public void onAttach(Context context) {//执行此方法 则说明会员Fragment 与Activity 绑定了，
        super.onAttach(context);

    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){//不在最前端界面显示，相当于调用了onPause()


        }else{//重新显示到最前端 ,相当于调用了onResume()

            //进行网络数据刷新  此处执行必须要在 Fragment与Activity绑定了 即需要添加判断是否完成绑定，否则将会报空（即非第一个显示出来的fragment，虽然onCreateView没有被调用,
            //但是onHiddenChanged也会被调用，所以如果你尝试去获取活动的话，注意防止出现空指针）

        }
    }

    private void setfuctionview() {
        List<FunctionBean> functionlist = new ArrayList<>();
        functionlist.add(new FunctionBean("Dialog展示", 0, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Dialog_ProgressActivity.startAction(context);
            }
        }));
        functionlist.add(new FunctionBean("Toast_LabelsView",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                ToastActivity.startAction(context);
            }
        }));
        functionlist.add(new FunctionBean("查看大图",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                List<BigImageBean> img_list = new ArrayList<>();
                img_list.add(new BigImageBean("http://a0.att.hudong.com/31/35/300533991095135084358827466.jpg"
                        ,"美女111"));
                img_list.add(new BigImageBean("http://a3.topitme.com/1/21/79/1128833621e7779211o.jpg"
                        ,"美女222"));
                img_list.add(new BigImageBean("http://x.itunes123.com/uploadfiles/1b13c3044431fb712bb712da97f42a2d.jpg"
                        ,"美女333"));
                img_list.add(new BigImageBean("http://x.itunes123.com/uploadfiles/a3864382d68ce93bb7ab84775cb12d17.jpg"
                        ,"美女444"));
                img_list.add(new BigImageBean("http://07.imgmini.eastday.com/mobile/20171109/20171109213644_1d934ed6d1143d2a63227336004e922a_1.jpeg"
                        ,"美女???"));
                String imglistjson = JsonTools.toJson(img_list);
                BigImagePagerActivity.startAction(context, imglistjson, 0);
            }
        }));
        functionlist.add(new FunctionBean("数据时效存储",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                ACacheActivity.startAction(context);
            }
        }));
        functionlist.add(new FunctionBean("爱心点赞-爆炸",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                HeartLikeViewActivity.startAction(context);
            }
        }));
        functionlist.add(new FunctionBean("图文混排",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                RichTextActivity.startAction(context,view);
            }
        }));
        functionlist.add(new FunctionBean("popupwindows",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                PopWinActivity.startAction(context,view);
            }
        }));
        functionlist.add(new FunctionBean("随机验证码",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                VerifyCodeActivity.startAction(context,view);
            }
        }));
        functionlist.add(new FunctionBean("RunTextView",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                RunTextActivity.startAction(context,view);
            }
        }));
        functionlist.add(new FunctionBean("设备信息",  R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                PhoneInfoActivity.startAction(context,view);
            }
        }));
        functionlist.add(new FunctionBean("拖拽式层叠卡片", R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                CardGroupActivity.startAction(context);
            }
        }));
        functionlist.add(new FunctionBean("登录", R.mipmap.pikachu_sit, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                LoginActivity.startAction(context);
            }
        }));
        functionlist.add(new FunctionBean("二维码", R.mipmap.s_code_icon, new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, ScanerCodeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, AppConstant.REQUEST_QRCODE);
            }
        }));
        functionadapter.replaceData(functionlist);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) { // Successfully.
            if (requestCode == AppConstant.REQUEST_QRCODE) {//扫码
                Bundle bundle = data.getExtras();
               String zxqrcode = bundle.getString("result");
                LogTools.e(TAG, "" + zxqrcode);
                if (!DataTools.isNullString(zxqrcode)) {
                    creatDialogBuilder()
                            .setDialog_title("扫描结果")
                            .setDialog_message(zxqrcode)
                            .setDialog_Left("确定")
                            .setDialog_Right("取消")
                            .setCancelable(true)
                            .builder().show();
                } else {
                    ToastTool.success("扫描失败!");
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) { // User canceled.
            LogTools.e(TAG, " User canceled.");
        }
    }

    private void clooserefreshlayout() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        homeRefreshlayout.finishRefresh();
                        homeRefreshlayout.finishLoadMore();
                    }
                });

    }
}

