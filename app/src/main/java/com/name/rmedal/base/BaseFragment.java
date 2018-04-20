package com.name.rmedal.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.name.rmedal.R;
import com.veni.rxtools.RxBarTool;
import com.veni.rxtools.base.AlertDialogBuilder;
import com.veni.rxtools.base.BaseModel;
import com.veni.rxtools.base.BasePresenter;
import com.veni.rxtools.base.FragmentBase;
import com.veni.rxtools.base.TUtil;
import com.veni.rxtools.baserx.RxManager;
import com.veni.rxtools.view.RxToast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：xiyn on 2017/01/30
 * 当前类注释:基类fragment
 */
public abstract class BaseFragment<T extends BasePresenter, E extends BaseModel> extends FragmentBase {

    protected View rootView;
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;
    private Unbinder unbinder;
    protected String TAG;

    //获取布局文件
    protected abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null && getLayoutId() != 0) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
        }
        mRxManager = new RxManager();
        context = getContext();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        TAG = getClass().getSimpleName();
        initPresenter();
        initView(savedInstanceState);
        return rootView;
    }

    protected void startProgressDialog(String loadtipmsg) {
        creatDialogBuilder()
                .setDialog_message(loadtipmsg)
                .setLoadingView(R.color.colorAccent)
                .builder().show();
    }

    protected void stopProgressDialog() {
        destroyDialogBuilder();
    }

    protected void stopErrorProgressDialog(String errtipmsg) {
        stopProgressDialog();
        if (errtipmsg.equals("")) {
            errtipmsg = getString(com.veni.rxtools.R.string.net_error);
        }
        RxToast.error(errtipmsg);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     * 设置透明状态栏
     * 增加 最顶部的子标签 高度
     * DisplayUtil.ViewinitState(view);
     * 最顶部的子标签 必须设置 android:fitsSystemWindows="true" 属性
     * 否则标题栏会被导航栏遮挡
     */
    protected void SetTranslanteBar() {
        RxBarTool.translucentStatusBar((Activity) context);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        mRxManager.clear();
    }


}
