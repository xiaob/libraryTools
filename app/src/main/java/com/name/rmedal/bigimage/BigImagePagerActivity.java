package com.name.rmedal.bigimage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.modelbean.BigImageBean;
import com.name.rmedal.tools.SystemUiVisibilityUtil;
import com.veni.rxtools.RxJsonTools;
import com.veni.rxtools.RxTabLayoutTool;
import com.veni.rxtools.base.RxActivityOptionsTool;
import com.veni.rxtools.interfaces.OnNoFastClickListener;
import com.veni.rxtools.view.RxViewPagerFixed;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：jaydenxiao
 * 当前类注释:
 * 查看大图 glide 多张
 */
public class BigImagePagerActivity extends BaseActivity {

    @BindView(R.id.photo_det_toolbar)
    Toolbar toolbar;
    @BindView(R.id.bigimage_pager)
    RxViewPagerFixed bigimagViewPager;
    @BindView(R.id.bigimage_guideGroup)
    TextView bigimageGuideGroup;//第几章图片 1/5
    @BindView(R.id.bigimage_title_tv)
    TextView bigimageTitleTv;//图片标题

    private boolean mIsToolBarHidden;
    private boolean mIsStatusBarHidden;

    public static final String INTENT_IMGLISTJSON = "img_list_json";
    public static final String INTENT_POSITION = "position";
    public static final String INTENT_TITLE = "needtitle";

    public static void startAction(Context context, String imgUrls, int position) {
        startAction(context, imgUrls, position, false);
    }

    public static void startAction(Context context, String imgUrls, int position, boolean needtitle) {
        new RxActivityOptionsTool().setContext(context)
                .setClass(BigImagePagerActivity.class)
                .setBundle(INTENT_IMGLISTJSON, imgUrls)
                .setBundle(INTENT_POSITION, position)
                .setBundle(INTENT_TITLE, needtitle)
                .customAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_bigimage_pager;
    }


    private List<BigImageBean> img_list = new ArrayList<>();
    private boolean needtitle;

    @Override
    public void initView(Bundle savedInstanceState) {
        setSwipeBackLayout(2);
        //设置透明状态栏
        SetTranslanteBar(toolbar);
        toolBarFadeIn();
        initToolbar();
        initBackground();

        int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        needtitle = getIntent().getBooleanExtra(INTENT_POSITION, false);
        String imgUrls = getIntent().getStringExtra(INTENT_IMGLISTJSON);
        img_list = RxJsonTools.parseArray(imgUrls, BigImageBean.class);

        setPhotoDetailTitle(startPos);

        ImageAdapter mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(img_list);
        bigimagViewPager.setAdapter(mAdapter);
        bigimagViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setPhotoDetailTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bigimagViewPager.setCurrentItem(startPos);
    }

    @Override
    public void initPresenter() {

    }

    private void initToolbar() {
        toolbar.setTitle("图片详情");
        onCreateCustomToolBar(context, toolbar, true);
    }

    public void setPhotoDetailTitle(int position) {
        if (img_list.size() > position) {
            if (img_list.size() == 1) {
                bigimageGuideGroup.setVisibility(View.GONE);
                bigimageTitleTv.setVisibility(View.GONE);
            } else {
                bigimageGuideGroup.setVisibility(View.VISIBLE);
                bigimageTitleTv.setVisibility(View.VISIBLE);
                String phototitle = img_list.get(position).getImage_describe();
                String view_title = img_list.get(position).getImage_view_title();
                if (needtitle && !view_title.equals("")) {
                    toolbar.setTitle(view_title);
                } else {
                    toolbar.setTitle("图片详情");
                }
                bigimageTitleTv.setText(phototitle);
                bigimageGuideGroup.setText(getString(R.string.photo_detail_num, position + 1,
                        img_list.size()));
            }
        } else {
            bigimageTitleTv.setVisibility(View.GONE);
            bigimageGuideGroup.setVisibility(View.GONE);
        }
    }

    private class ImageAdapter extends PagerAdapter {

        private List<BigImageBean> datas = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;

        public void setDatas(List<BigImageBean> datas) {
            if (datas != null)
                this.datas = datas;
        }

        public ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            if (datas == null) return 0;
            return datas.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);
            ImageView imageView = view.findViewById(R.id.image);

            String path = this.datas.get(position).getImage_url();
            Uri uri;
            if (path.startsWith("http")) {
                uri = Uri.parse(path);
            } else {
                uri = Uri.fromFile(new File(path));
            }
            imageView.setOnClickListener(new OnNoFastClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    hideOrShowToolbar();
                    hideOrShowStatusBar();
                }
            });

            //loading
            final ProgressBar loading = new ProgressBar(context);
            FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            loadingLayoutParams.gravity = Gravity.CENTER;
            loading.setLayoutParams(loadingLayoutParams);
            ((FrameLayout) view).addView(loading);


            loading.setVisibility(View.VISIBLE);
            Glide.with(context).load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(me.iwf.photopicker.R.drawable.__picker_ic_photo_black_48dp)
                    .error(R.drawable.ic_empty_picture)
                    .dontAnimate().dontTransform().override(800, 800)
                    .thumbnail(0.1f)
                    .listener(new RequestListener<Uri, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            loading.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imageView);

            container.addView(view, 0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }


    private void initBackground() {
        ColorDrawable mBackground = new ColorDrawable(Color.BLACK);
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
