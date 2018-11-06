package com.veni.tools.view.imageload;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.veni.tools.R;

/**
 * 作者：kkan on 2017/01/30
 * 当前类注释:
 * 图片加载工具类 使用{@link Glide }4.8.0 框架封装
 * <p>
 * <p>
 * ImageLoaderTool.with(context).loadUrl(ImagePath).into(imageView);
 */
public class ImageLoaderTool {

    public static ImageLoaderTool.Builder with(Context context) {
        return new Builder(context);
    }

    public static class Builder {
        private Context context;

        private RequestOptions options;

        private RequestListener<Drawable> requestListener;

        private Uri uri;

        private int width;

        public Builder(@NonNull Context context) {
            this.context = context;
            this.options = new RequestOptions()
//                    .placeholder(R.drawable.ic_image_loading)//加载成功之前占位图
                    .error(R.drawable.ic_empty_picture)//加载错误之后的错误图
                    //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                    .fitCenter()
                    //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的宽高都大于等于ImageView的宽度，然后截取中间的显示。）
//                    .centerCrop()
                    .priority(Priority.HIGH)// 当前线程的优先级
//                    .skipMemoryCache(true)//跳过内存缓存
//                    .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存所有版本的图像
                    .diskCacheStrategy(DiskCacheStrategy.NONE);//跳过磁盘缓存
//                    .diskCacheStrategy(DiskCacheStrategy.DATA);//只缓存原来分辨率的图片
//                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE);//只缓存最终的图片

        }

        public Builder setRequestListener(RequestListener<Drawable> requestListener) {
            this.requestListener = requestListener;
            return this;
        }

        /**
         * 加载圆角图片
         */
        @SuppressLint("CheckResult")
        public Builder loadRound(int roundingRadius) {
            //设置图片圆角角度
            RoundedCorners roundedCorners = new RoundedCorners(roundingRadius);
            options.transform(roundedCorners);
            return this;
        }

        /**
         * 加载圆形图片
         */
        @SuppressLint("CheckResult")
        public Builder loadCircle() {
            options.circleCrop();
            options.autoClone();
            return this;
        }

        /**
         *指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
         */
        @SuppressLint("CheckResult")
        public Builder centerCrop() {
            options.centerCrop();
            return this;
        }

        /**
         * 关闭动画
         */
        @SuppressLint("CheckResult")
        public Builder dontAnimate() {
            options.dontAnimate();
            return this;
        }

        /**
         * 关闭缩略图动画
         */
        @SuppressLint("CheckResult")
        public Builder dontTransform() {
            options.dontTransform();
            return this;
        }

        /**
         *采样率 指定图片的尺寸
         */
        @SuppressLint("CheckResult")
        public Builder override(int width, int height) {
            this.width = width;
            options.override(width, height);//采样率 指定图片的尺寸
            return this;
        }

        /**
         * 设置加载的url
         */
        @SuppressLint("CheckResult")
        public Builder loadUrl(@NonNull String url) {
            this.uri = Uri.parse(url);
            return this;
        }

        /**
         * 设置加载的url
         */
        @SuppressLint("CheckResult")
        public Builder loadUrl(@NonNull Uri uri) {
            this.uri = uri;
            return this;
        }

        /**
         * 设置加载的ImageView
         */
        public void into(@NonNull ImageView imageView) {
            if (width == 0) {
                //采样率 指定图片的尺寸
                override(400, 400);
            }
            Glide.with(context).load(uri)
                    .apply(options)
                    .listener(requestListener)
                    .thumbnail(0.25f)//图片加载时显示 原图的缩略图
                    .transition(DrawableTransitionOptions.withCrossFade()).into(imageView);//交叉淡入

        }

    }
}
