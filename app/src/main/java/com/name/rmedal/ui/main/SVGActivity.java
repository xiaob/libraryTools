package com.name.rmedal.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.jaredrummler.android.widget.AnimatedSvgView;
import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.modelbean.ModelSVG;
import com.veni.tools.StatusBarUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SVGActivity extends BaseActivity {

    @BindView(R.id.animated_svg_view)
    AnimatedSvgView mSvgView;
    @BindView(R.id.app_name)
    ImageView mAppName;
    private Handler checkhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mAppName.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_svg;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        StatusBarUtil.immersive(this);
        setSvg(ModelSVG.values()[0]);
        CheckUpdate();

    }

    private void setSvg(ModelSVG modelSvg) {
        mSvgView.setGlyphStrings(modelSvg.glyphs);
        mSvgView.setFillColors(modelSvg.colors);
        mSvgView.setViewportSize(modelSvg.width, modelSvg.height);
        mSvgView.setTraceResidueColor(0x32000000);
        mSvgView.setTraceColors(modelSvg.colors);
        mSvgView.rebuildGlyphData();
        mSvgView.start();
    }

    /**
     * 检查是否有新版本，如果有就升级
     */
    private void CheckUpdate() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        MainActivity.startAction(context);
                    }
                });
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Message msg = checkhandler.obtainMessage();
                        checkhandler.sendMessage(msg);
                    }
                });
    }
}
