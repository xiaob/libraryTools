package com.name.rmedal.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.name.rmedal.R;
import com.veni.tools.base.BaseModel;
import com.veni.tools.base.BasePresenter;
import com.veni.tools.base.FragmentBase;
import com.veni.tools.base.TUtil;
import com.veni.tools.baserx.RxManager;
import com.veni.tools.interfaces.AntiShake;
import com.veni.tools.view.ToastTool;

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
    protected AntiShake antiShake;//防止重复点击

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
        antiShake = new AntiShake();
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
            errtipmsg = getString(com.veni.tools.R.string.net_error);
        }
        ToastTool.error(errtipmsg);
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
