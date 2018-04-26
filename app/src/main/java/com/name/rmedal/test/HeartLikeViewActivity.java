package com.name.rmedal.test;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.veni.tools.LogTool;
import com.veni.tools.StatusBarUtil;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.view.TitleView;
import com.veni.tools.view.heart.HeartLayout;
import com.veni.tools.view.likeview.ShineButton;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 * 爱心点赞
 */

public class HeartLikeViewActivity extends BaseActivity {

    @BindView(R.id.heartlike_title_view)
    TitleView heartlikeTitleView;

    @BindView(R.id.po_image0)
    ShineButton mShineButton;
    @BindView(R.id.po_image1)
    ShineButton porterShapeImageView1;
    @BindView(R.id.po_image2)
    ShineButton porterShapeImageView2;
    @BindView(R.id.po_image3)
    ShineButton porterShapeImageView3;
    @BindView(R.id.wrapper)
    LinearLayout mWrapper;
    @BindView(R.id.po_image8)
    ShineButton mPoImage8;
    @BindView(R.id.heart_layout)
    HeartLayout mHeartLayout;
    @BindView(R.id.tv_hv)
    TextView mTvHv;

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new ActivityJumpOptionsTool().setContext(context)
                .setClass(HeartLikeViewActivity.class)
                .customAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_heartlike;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, heartlikeTitleView);
        heartlikeTitleView.setLeftFinish(context);
        heartlikeTitleView.setTitle("点赞");
        setSwipeBackLayout(0);


        mShineButton.init(this);

        porterShapeImageView1.init(this);
        porterShapeImageView2.init(this);
        porterShapeImageView3.init(this);
        porterShapeImageView3.setShineTurnAngle(1);

        ShineButton shinebuttonjava = new ShineButton(this);

        shinebuttonjava.setBtnColor(Color.GRAY);
        shinebuttonjava.setBtnFillColor(Color.RED);
        shinebuttonjava.setShapeResource(R.mipmap.heart);
        shinebuttonjava.setAllowRandomColor(true);
        shinebuttonjava.setShineSize(100);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
        shinebuttonjava.setLayoutParams(layoutParams);
        if (mWrapper != null) {
            mWrapper.addView(shinebuttonjava);
        }


    }

    private Random random = new Random();

    @OnClick({R.id.po_image1, R.id.po_image2, R.id.po_image3, R.id.po_image0, R.id.po_image8, R.id.love, R.id.heart_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.po_image1:
                break;
            case R.id.po_image2:
                break;
            case R.id.po_image3:
                break;
            case R.id.po_image0:
                break;
            case R.id.po_image8:
                break;
            case R.id.love:
                LogTool.e(TAG, "-----");
                mHeartLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        int rgb = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
                        mHeartLayout.addHeart(rgb);
                    }
                });
                break;
            case R.id.heart_layout:
                break;
        }
    }
}
