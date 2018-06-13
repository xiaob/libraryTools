package com.name.rmedal.ui.main;

import android.content.Context;
import android.os.Bundle;


import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.veni.tools.SPTools;
import com.veni.tools.StatusBarTools;
import com.veni.tools.base.ActivityJumpOptionsTool;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

/**
 * 作者：xiyn on 2017/11/30
 * 当前类注释:
 *      引导页 activity
 *
 */
public class FirstStartActivity extends BaseActivity {

    @BindView(R.id.banner_guide_background)
    BGABanner mBackgroundBanner;
    @BindView(R.id.banner_guide_foreground)
    BGABanner mForegroundBanner;

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new ActivityJumpOptionsTool().setContext(context)
                .setClass(FirstStartActivity.class)
                .setExitResId(0)
                .setFinish(true)
                .customAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_first_start;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {


        StatusBarTools.immersive(this);
//        StatusBarTools.setPaddingSmart(this, toastTitleView);

        SPTools.put(this, SPTools.FIRST_TIME, false);

        setListener();
        processLogic();
    }

    private void setListener() {
        /**
         * 设置进入按钮和跳过按钮控件资源 id 及其点击事件
         * 如果进入按钮和跳过按钮有一个不存在的话就传 0
         * 在 BGABanner 里已经帮开发者处理了防止重复点击事件
         * 在 BGABanner 里已经帮开发者处理了「跳过按钮」和「进入按钮」的显示与隐藏
         */

        mForegroundBanner.setEnterSkipViewIdAndDelegate(R.id.btn_guide_enter, R.id.tv_guide_skip, new BGABanner.GuideDelegate() {
            @Override
            public void onClickEnterOrSkip() {
                MainActivity.startAction(context);
            }
        });
    }

    private void processLogic() {
        // 设置数据源
        mBackgroundBanner.setData(R.mipmap.uoko_guide_background_1, R.mipmap.uoko_guide_background_1, R.mipmap.uoko_guide_background_1);

        mForegroundBanner.setData(R.mipmap.uoko_guide_foreground_1, R.mipmap.uoko_guide_foreground_1, R.mipmap.uoko_guide_foreground_1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 如果开发者的引导页主题是透明的，需要在界面可见时给背景 Banner 设置一个白色背景，避免滑动过程中两个 Banner 都设置透明度后能看到 Launcher
        mBackgroundBanner.setBackgroundResource(android.R.color.white);
    }
}
