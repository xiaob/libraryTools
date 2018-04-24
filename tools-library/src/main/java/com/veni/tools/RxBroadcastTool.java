package com.veni.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.veni.tools.view.RxToast;

/**
 * Created by xiyn on 2017/3/15.
 * BroadcastReceiverNetWork    : 监听网络状态改变的广播
 * initRegisterReceiverNetWork : 注册监听网络状态的广播
 */
public class RxBroadcastTool {

    /**
     * 注册监听网络状态的广播
     *
     * @param context
     * @return
     */
    public static BroadcastReceiverNetWork initRegisterReceiverNetWork(Context context) {
        // 注册监听网络状态的服务
        BroadcastReceiverNetWork mReceiverNetWork = new BroadcastReceiverNetWork();
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(mReceiverNetWork, mFilter);
        return mReceiverNetWork;
    }

    /**
     * 网络状态改变广播
     */
    public static class BroadcastReceiverNetWork extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int netType = RxNetTool.getNetWorkType(context);

            switch (netType) {//获取当前网络的状态
                case RxNetTool.NETWORK_WIFI:// wifi的情况下
                    RxToast.success("切换到wifi环境下");
                    break;
                case RxNetTool.NETWORK_2G:
                    RxToast.info("切换到2G环境下");
                    break;
                case RxNetTool.NETWORK_3G:
                    RxToast.info("切换到3G环境下");
                    break;
                case RxNetTool.NETWORK_4G:
                    RxToast.info("切换到4G环境下");
                    break;
                case RxNetTool.NETWORK_NO:
                    RxToast.error(context, "当前无网络连接").show();
                    break;
                case RxNetTool.NETWORK_UNKNOWN:
                    RxToast.normal("未知网络");
                    break;
            }

        }
    }
}
