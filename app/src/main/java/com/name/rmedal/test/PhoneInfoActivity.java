package com.name.rmedal.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.name.rmedal.R;
import com.name.rmedal.api.AppConstant;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.modelbean.DeviceBean;
import com.name.rmedal.tools.TextViewTools;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.veni.tools.DeviceTools;
import com.veni.tools.StatusBarTools;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.view.TitleView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 */

public class PhoneInfoActivity extends BaseActivity {

    @BindView(R.id.phone_info_title)
    TitleView phoneInfoTitle;
    @BindView(R.id.phone_info_get_btn)
    Button phoneInfoGetBtn;
    @BindView(R.id.phone_info_refreshlayout)
    SmartRefreshLayout toastRefreshlayout;
    @BindView(R.id.phone_info_recyclerview)
    RecyclerView toastRecyclerview;

    /**
     * 启动入口
     */
    public static void startAction(Context context, View view) {
        new ActivityJumpOptionsTool().setContext(context)
                .setClass(PhoneInfoActivity.class)
                .setView(view)
                .setActionString(AppConstant.TRANSITION_ANIMATION)
                .screenTransitAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_device_info;
    }

    @Override
    public void initPresenter() {

    }

    private BaseQuickAdapter<DeviceBean, BaseViewHolder> functionadapter;
    private List<DeviceBean> devicelist = new ArrayList<>();
    @Override
    public void initView(Bundle savedInstanceState) {
        View view = phoneInfoTitle.getTvTitle();
        ViewCompat.setTransitionName(view, AppConstant.TRANSITION_ANIMATION);
        StatusBarTools.immersive(this);
        StatusBarTools.setPaddingSmart(this, phoneInfoTitle);
        phoneInfoTitle.setLeftFinish(context);
        phoneInfoTitle.setTitle("设备信息");
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

        functionadapter =new BaseQuickAdapter<DeviceBean, BaseViewHolder>(R.layout.activity_device_info_item) {
            @Override
            protected void convert(BaseViewHolder viewHolder, DeviceBean item) {
                TextView info_item_tv = viewHolder.getView(R.id.phone_info_item_tv);
                TextViewTools.upitemtvforhtml_onetv(info_item_tv,item.getUserName(),item.getNickName());
            }
        };

        functionadapter.openLoadAnimation();
        toastRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        toastRecyclerview.setAdapter(functionadapter);
    }

    @OnClick({R.id.phone_info_get_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phone_info_get_btn:
                getPhoneInfo();
                phoneInfoGetBtn.setVisibility(View.GONE);
                break;
        }
    }

    private void getPhoneInfo() {
        devicelist.add(new DeviceBean("是否为手机", DeviceTools.isPhone(context)?"是":"否"));
        devicelist.add(new DeviceBean("手机类型", DeviceTools.getPhoneType(context)+""));
        devicelist.add(new DeviceBean("设备密度", DeviceTools.getScreenDensity(context)+""));
        devicelist.add(new DeviceBean("唯一标识序列号", DeviceTools.getUniqueSerialNumber()+""));
        devicelist.add(new DeviceBean("设备宽度", DeviceTools.getScreenWidth(context)+""));
        devicelist.add(new DeviceBean("设备高度", DeviceTools.getScreenHeight(context)+""));
        devicelist.add(new DeviceBean("当前版本名称", DeviceTools.getAppVersionName(context)+""));
        devicelist.add(new DeviceBean("当前版本号", DeviceTools.getAppVersionNo(context)+""));
        devicelist.add(new DeviceBean("设备IMEI", DeviceTools.getDeviceIdIMEI(context)+""));
        devicelist.add(new DeviceBean("设备IMSI", DeviceTools.getIMSI(context)+""));
        devicelist.add(new DeviceBean("设备软件版本号", DeviceTools.getDeviceSoftwareVersion(context)+""));
        devicelist.add(new DeviceBean("设备MAC地址", DeviceTools.getMacAddress(context)+""));
        devicelist.add(new DeviceBean("MCC+MNC", DeviceTools.getNetworkOperator(context)+""));
        devicelist.add(new DeviceBean("网络注册名称", DeviceTools.getNetworkOperatorName(context)+""));
        devicelist.add(new DeviceBean("SIM国家码", DeviceTools.getNetworkCountryIso(context)+""));
        devicelist.add(new DeviceBean("SIM网络码", DeviceTools.getSimOperator(context)+""));
        devicelist.add(new DeviceBean("SIM序列号", DeviceTools.getSimSerialNumber(context)+""));
        devicelist.add(new DeviceBean("SIM状态", DeviceTools.getSimState(context)+""));
        devicelist.add(new DeviceBean("服务商名称", DeviceTools.getSimOperatorName(context)+""));
        devicelist.add(new DeviceBean("SubscriberId", DeviceTools.getSubscriberId(context)+""));
        devicelist.add(new DeviceBean("语音邮件号码", DeviceTools.getVoiceMailNumber(context)+""));
        devicelist.add(new DeviceBean("ANDROID ID", DeviceTools.getAndroidId(context)+""));
        devicelist.add(new DeviceBean("设备型号", DeviceTools.getBuildBrandModel()+""));
        devicelist.add(new DeviceBean("设备厂商", DeviceTools.getBuildMANUFACTURER()+""));
        devicelist.add(new DeviceBean("设备品牌", DeviceTools.getBuildBrand()+""));
        devicelist.add(new DeviceBean("序列号", DeviceTools.getSerialNumber()+""));
        devicelist.add(new DeviceBean("国际长途区号", DeviceTools.getNetworkCountryIso(context)+""));
        devicelist.add(new DeviceBean("手机号", DeviceTools.getLine1Number(context)+""));
        functionadapter.replaceData(devicelist);
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
                    }
                });
    }
}
