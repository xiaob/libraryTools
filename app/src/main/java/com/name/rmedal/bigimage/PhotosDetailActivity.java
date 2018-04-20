package com.name.rmedal.bigimage;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.name.rmedal.R;
import com.name.rmedal.api.AppConstant;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.tools.SystemUiVisibilityUtil;
import com.veni.rxtools.RxTabLayoutTool;
import com.veni.rxtools.base.RxActivityOptionsTool;
import com.veni.rxtools.interfaces.OnNoFastClickListener;

import java.io.File;

import butterknife.BindView;

/**
 * 大图详情 单张
 */
public class PhotosDetailActivity extends BaseActivity {

    @BindView(R.id.photo_touch_iv)
    ImageView photoTouchIv;
    @BindView(R.id.photo_det_toolbar)
    Toolbar toolbar;
    @BindView(R.id.background)
    RelativeLayout background;
    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;
    private ColorDrawable mBackground;


    public static final String PHOTO_DETAIL = "photo_detail";//大图详情 地址
    public static final String PHOTO_TITLE = "photo_title";//大图详情 标题
    public static void startAction(Context context, View view, String url, String title) {

        new RxActivityOptionsTool().setContext(context)
                .setClass(PhotosDetailActivity.class)
                .setBundle(PHOTO_DETAIL, url)
                .setBundle(PHOTO_TITLE, title)
                .setView(view)
                .setActionString(AppConstant.TRANSITION_ANIMATION)
                .screenTransitAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setSwipeBackLayout(0);
        ViewCompat.setTransitionName(photoTouchIv, AppConstant.TRANSITION_ANIMATION);
        SetTranslanteBar(toolbar);
        toolBarFadeIn();
        initToolbar();
        initBackground();
        loadPhotoIv();
        initImageView();
        setPhotoViewClickEvent();
    }

    private void initToolbar() {
        String title = getIntent().getStringExtra(PHOTO_TITLE);
        if(title==null||title.equals("")){
            title="图片浏览";
        }
        toolbar.setTitle(title);
        onCreateCustomToolBar(context,toolbar,true);
    }

    private void initImageView() {
        loadPhotoIv();
    }

    private void loadPhotoIv() {
        String path = getIntent().getStringExtra(PHOTO_DETAIL);
        Uri uri;
        if (path.startsWith("http")) {
            uri = Uri.parse(path);
        } else {
            uri = Uri.fromFile(new File(path));
        }
        Glide.with(this).load(uri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_image_loading)
                .error(R.drawable.ic_empty_picture)
                .dontAnimate().dontTransform().override(800, 800)
                .thumbnail(0.1f).into(photoTouchIv);
    }

    private void setPhotoViewClickEvent() {
        photoTouchIv.setOnClickListener(new OnNoFastClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                hideOrShowToolbar();
                hideOrShowStatusBar();
            }
        });
    }

    private void initBackground() {
        mBackground = new ColorDrawable(Color.BLACK);
        RxTabLayoutTool.getRootView(this).setBackgroundDrawable(mBackground);
    }


    protected void hideOrShowToolbar() {
        toolbar.animate()
                .alpha(mIsToolBarHidden ? 1.0f : 0.0f)
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        mIsToolBarHidden = !mIsToolBarHidden;
    }

    private void hideOrShowStatusBar() {
        if (mIsStatusBarHidden) {
            SystemUiVisibilityUtil.enter(context);
        } else {
            SystemUiVisibilityUtil.exit(context);
        }
        mIsStatusBarHidden = !mIsStatusBarHidden;
    }

    private void toolBarFadeIn() {
        mIsToolBarHidden = true;
        hideOrShowToolbar();
    }

    /**
     * @param homeAsUpEnabled 是否可点击
     */
    public void onCreateCustomToolBar(Context context, Toolbar toolbarBaseTb, boolean homeAsUpEnabled) {
        ((AppCompatActivity) context).setSupportActionBar(toolbarBaseTb);
        toolbarBaseTb.setTitleTextColor(ContextCompat.getColor(context,
                R.color.primary_text_default_material_dark));
        if (homeAsUpEnabled) {
            ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setDisplayUseLogoEnabled(false);
                actionBar.setHomeAsUpIndicator(R.drawable.icon_previous);
            }
        }
    }
}
