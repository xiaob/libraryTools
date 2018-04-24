package com.name.rmedal.base;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;

import com.gw.swipeback.SwipeBackLayout;
import com.name.rmedal.BuildConfig;
import com.name.rmedal.R;
import com.veni.tools.base.ActivityBase;
import com.veni.tools.base.BaseModel;
import com.veni.tools.base.BasePresenter;
import com.veni.tools.base.TUtil;
import com.veni.tools.baserx.RxManager;
import com.veni.tools.view.ToastTool;

import butterknife.ButterKnife;

/**
 * 作者：xiyn on 2017/01/30
 * 当前类注释:
 * 基类Activity
 */
public abstract class BaseActivity<T extends BasePresenter, E extends BaseModel> extends ActivityBase {

    public T mPresenter;
    public E mModel;
    protected RxManager mRxManager;
    protected SwipeBackLayout swipeBackLayout;
    protected String TAG;

    /*********************子类实现*****************************/
    //获取布局文件
    public abstract int getLayoutId();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    public abstract void initView(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetcontentView();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        doAfterSetcontentView();
        this.initPresenter();
        this.initView(savedInstanceState);
    }

    /**
     * 设置layout前配置
     */
    private void doBeforeSetcontentView() {
        mRxManager = new RxManager();
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 设置layout后配置
     */
    private void doAfterSetcontentView() {
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this;
        }
        TAG = context.getClass().getSimpleName();
        setSwipeBackLayout(-1);
    }

    /**
     * 支持滑动返回
     * -1 关闭 0左滑 1 右滑 2 上滑 3下滑
     * direction 滑动方向
     * activity style 必须设置Theme.Swipe.Back.NoActionBar
     */
    public void setSwipeBackLayout(int issupswipebace/*,boolean isSwipeFromEdge*/) {
        int direction = -1;
        switch (issupswipebace) {
            case 0:
                direction = SwipeBackLayout.FROM_LEFT;
                break;
            case 1:
                direction = SwipeBackLayout.FROM_RIGHT;
                break;
            case 2:
                direction = SwipeBackLayout.FROM_TOP;
                break;
            case 3:
                direction = SwipeBackLayout.FROM_BOTTOM;
                break;
        }
        if (direction != -1) {
            if(swipeBackLayout == null){
                swipeBackLayout = new SwipeBackLayout(this);
                swipeBackLayout.attachToActivity(this);
                swipeBackLayout.setMaskAlpha(125);
                swipeBackLayout.setSwipeBackFactor(0.5f);
            }
            swipeBackLayout.setDirectionMode(direction);
            swipeBackLayout.setSwipeFromEdge(false);//是否只能从边缘滑动退出
        }
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
    protected void onResume() {
        super.onResume();
        //debug版本不统计crash
        if (!BuildConfig.LOG_DEBUG) {
            //统计
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //debug版本不统计crash
        if (!BuildConfig.LOG_DEBUG) {
            //统计
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.onDestroy();
        if (mRxManager != null) {
            mRxManager.clear();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
