package com.name.rmedal.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.ui.main.contract.MainContract;
import com.name.rmedal.ui.main.model.MainModel;
import com.name.rmedal.ui.main.presenter.MainPresenter;
import com.name.rmedal.ui.personal.PersonalFragment;
import com.name.rmedal.ui.trade.TradeFragment;
import com.veni.tools.ActivityTools;
import com.veni.tools.LogTools;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.StatusBarTools;
import com.veni.tools.view.ToastTool;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View {

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new ActivityJumpOptionsTool().setContext(context)
                .setClass(MainActivity.class)
                .setEnterResId(0)
                .setActionTag(ActivityJumpOptionsTool.Type.CLEAR_TASK)
                .customAnim()
                .start();
    }

    @BindView(R.id.main_bottom_navigation)
    AHBottomNavigation mainBottomNavigation;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        StatusBarTools.immersive(this);
        initBottomNavigation();
        mainBottomNavigation.setCurrentItem(0, true);
//        mPresenter.checkVersion("1");
    }

    /**
     * 版本检测返回数据
     */
    @Override
    public void returnVersionData(String data) {
        LogTools.e(TAG,"版本检测返回数据----"+data);

    }

    private HomeFragment homeFragment;
    private TradeFragment tradeFragment;
    private PersonalFragment personalFragment;

    private void initBottomNavigation() {
        AHBottomNavigationItem homepage = new AHBottomNavigationItem(R.string.homepage, R.mipmap.ic_main_homepage, android.R.color.white);
        AHBottomNavigationItem tradepage = new AHBottomNavigationItem(R.string.trade, R.mipmap.ic_main_trade, android.R.color.white);
        AHBottomNavigationItem vippage = new AHBottomNavigationItem(R.string.personal, R.mipmap.ic_main_personal, android.R.color.white);

        mainBottomNavigation.addItem(homepage);
        mainBottomNavigation.addItem(tradepage);
        mainBottomNavigation.addItem(vippage);

        mainBottomNavigation.setAccentColor(ContextCompat.getColor(context,
                R.color.colorPrimary));
        mainBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        mainBottomNavigation.setForceTint(true);
        mainBottomNavigation.setForceTitlesDisplay(true);
        mainBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, boolean wasSelected) {
                AHBottomNavigationItem selecetitem = mainBottomNavigation.getItem(position);
                SwitchTo(selecetitem);
            }
        });
    }

    private void SwitchTo(AHBottomNavigationItem selecetitem) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        hideFragments(transaction);
        String title = selecetitem.getTitle(context);
        switch (title) {
            case "首页"://首页
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.main_framelayout, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case "购物"://购物
                if (tradeFragment == null) {
                    tradeFragment = new TradeFragment();
                    transaction.add(R.id.main_framelayout, tradeFragment);
                } else {
                    transaction.show(tradeFragment);
                }
                break;

            case "我"://我
                if (personalFragment == null) {
                    personalFragment = new PersonalFragment();
                    transaction.add(R.id.main_framelayout, personalFragment);
                } else {
                    transaction.show(personalFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (tradeFragment != null) {
            transaction.hide(tradeFragment);
        }
        if (personalFragment != null) {
            transaction.hide(personalFragment);
        }
    }

    // 用来判断 两次返回键退出app
    private boolean isExit = false;

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            ToastTool.normal("再按一次退出程序");
            Observable.timer(2000, TimeUnit.MILLISECONDS)
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            isExit = false;
                        }
                    });

            return;
        }
        ActivityTools.getActivityTool().AppExit(this, false);
    }

    @Override
    public void showLoading(String loadtipmsg) {
        startProgressDialog(loadtipmsg);
    }

    @Override
    public void stopLoading() {
        stopProgressDialog();
    }

    @Override
    public void showErrorTip(String errtipmsg) {
        stopErrorProgressDialog(errtipmsg);
    }

}
