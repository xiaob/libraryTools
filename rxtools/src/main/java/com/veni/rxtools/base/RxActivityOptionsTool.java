package com.veni.rxtools.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.veni.rxtools.R;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：xiyn on 2017/12/22
 * 当前类注释:
 */

public class RxActivityOptionsTool {
    public enum Type {
        NEW_TASK,
        CLEAR_TASK
    }

    private Context context;
    private int enterResId = R.anim.dock_right_enter;//进入动画
    private int exitResId = R.anim.dock_left_exit;//退出动画
    private int bitmapid = R.drawable.set;//新的activity将通过这个bitmap渐变拉伸出现，新的activity初始大小就是这个bitmap的大小
    private int startWidth = 0;//新activity出现的初始X坐标，这个坐标是相对于source的左上角X坐标
    private int startHeight = 0;//新activity出现的初始Y坐标，这个坐标相对于source的左上角Y坐标
    private Intent intent;
    private Class<?> clas;
    private Bundle bundle;
    private View view;//用户确定新activity启动的初始坐标
    private String actionString = "";//view动画的标识
    private ActivityOptionsCompat options = null;

    private Type actionTag = Type.NEW_TASK;//intent标识
    private boolean finish = false;//是否关闭act

    public RxActivityOptionsTool setContext(Context context) {
        this.context = context;
        return this;
    }

    public RxActivityOptionsTool setClass(Class<?> clas) {
        this.clas = clas;
        return this;
    }

    public RxActivityOptionsTool setBundle(String key, Object value) {
        if (this.bundle == null) {
            this.bundle = new Bundle();
        }
        if (value == null) {
            return this;
        }
        if (value instanceof String && value.toString().length() > 0) {
            bundle.putString(key, (String) value);
        } else if (value instanceof Integer) {
            bundle.putInt(key, (Integer) value);
        } else if (value instanceof Double) {
            bundle.putDouble(key, (Double) value);
        } else if (value instanceof Boolean) {
            bundle.putBoolean(key, (Boolean) value);
        } else if (value.getClass().isArray() && Array.getLength(value) > 0) {
            List<Object> list = (List<Object>) value;
            Object object = list.get(0);
            if (object instanceof String) {
                bundle.putStringArrayList(key, (ArrayList<String>) value);
            }
        } else if (value instanceof Serializable) {
            bundle.putSerializable(key, (Serializable) value);
        } else {
            bundle.putString(key, value + "");
        }

        return this;
    }

    public RxActivityOptionsTool setActionTag(@NonNull Type actionTag) {
        this.actionTag = actionTag;
        return this;
    }

    public RxActivityOptionsTool setFinish(boolean finish) {
        this.finish = finish;
        return this;
    }

    public RxActivityOptionsTool setEnterResId(int enterResId) {
        this.enterResId = enterResId;
        return this;
    }

    public RxActivityOptionsTool setExitResId(int exitResId) {
        this.exitResId = exitResId;
        return this;
    }

    public RxActivityOptionsTool setView(View view) {
        this.view = view;
        return this;
    }

    public RxActivityOptionsTool setStartWidth(int startWidth) {
        this.startWidth = startWidth;
        return this;
    }

    public RxActivityOptionsTool setStartHeight(int startHeight) {
        this.startHeight = startHeight;
        return this;
    }

    public RxActivityOptionsTool setBitmapid(int bitmapid) {
        this.bitmapid = bitmapid;
        return this;
    }

    public RxActivityOptionsTool setActionString(String actionString) {
        this.actionString = actionString;
        return this;
    }

    private Intent getIntent() {
        if (context != null && clas != null) {
            intent = new Intent(context, clas);
            addintentflag(actionTag);
            if (bundle != null) intent.putExtras(bundle);
        }
        return intent;
    }

    /**
     * 启动activity
     */
    public void start(int requestCode) {
        getIntent();
        if (context == null || options == null || intent == null) {
            return;
        }
        addintentflag(Type.NEW_TASK);
        ActivityCompat.startActivityForResult((Activity) context, intent, requestCode, options.toBundle());

    }

    /**
     * 启动activity
     */
    public void start() {
        getIntent();
        if (context == null || options == null || intent == null) {
            return;
        }
        ActivityCompat.startActivity(context, intent, options.toBundle());
        if (finish || actionTag == Type.CLEAR_TASK) {
            ((Activity) context).finish();
        }
    }

    private void addintentflag(Type actionTag) {
        if (actionTag == Type.CLEAR_TASK) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        } else if (actionTag == Type.NEW_TASK) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    /**
     * 设置自定义的Activity动画，出入的是动画资源的id。
     * makeCustomAnimation
     * enterId：进入动画的资源ID
     * exitId：退出动画的资源ID
     */
    public RxActivityOptionsTool customAnim() {
        this.options = ActivityOptionsCompat.makeCustomAnimation(context,
                enterResId, exitResId);
        return this;
    }

    /**
     * 新的Activity从某个位置以某个大小出现，然后慢慢拉伸渐变到整个屏幕
     * makeScaleUpAnimation
     * source：一个view对象，用户确定新activity启动的初始坐标
     * startX：新activity出现的初始X坐标，这个坐标是相对于source的左上角X坐标
     * startY：新activity出现的初始Y坐标，这个坐标相对于source的左上角Y坐标
     * width：新activity初始的宽度
     * height：新activity初始的高度
     */
    public RxActivityOptionsTool scaleUpAnim() {
        this.options = ActivityOptionsCompat.makeScaleUpAnimation(view,
                view.getWidth() / 2, view.getHeight() / 2, startWidth, startHeight);
        return this;
    }

    /**
     * 一个bitmap慢慢从某个位置拉伸渐变新的activity
     * makeThumbnailScaleUpAnimation
     * source：一个view对象，用来确定起始坐标
     * thumbnail：一个bitmap对象，新的activity将通过这个bitmap渐变拉伸出现，新的activity初始大小就是这个bitmap的大小
     * startX：新activity初始的X坐标，相对于source左上角的X来说的
     * startY：新的activity初始的Y坐标，相对于source左上角Y坐标来说的
     */

    public RxActivityOptionsTool thumbNailScaleAnim() {
        Bitmap bitmap;
        if (bitmapid == 0) {
//            if (view instanceof ImageView) {
            view.setDrawingCacheEnabled(true);
            bitmap = view.getDrawingCache();
//            } else {
//                bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
//            }
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), bitmapid);
        }
        this.options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, bitmap, 0, 0);
        return this;
    }

    /**
     * 原始activity中的一个view随着新activity的慢慢启动而移动到新的activity中，实现补间动画
     * makeSceneTransitionAnimation
     * activity：当前activity的对象
     * sharedElement：一个view对象，用来和新的activity中的一个view对象产生动画
     * sharedElemetId:新的activity中的view的Id，这个view是用来和原始activity中的view产生动画的
     * <p>
     * 在初始化view设置
     * ViewCompat.setTransitionName(iv_a,"options5");
     */
    public RxActivityOptionsTool screenTransitAnim() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return screenTransitAnimByPair(new Pair<>(view, actionString));
        } else {
            return scaleUpAnim();
        }
    }

    /**
     * 原始activity中的一个view随着新activity的慢慢启动而移动到新的activity中，实现补间动画
     * makeSceneTransitionAnimation
     * activity：当前的activity
     * sharedElements：Pair对象，上面的一个方法是实现单一view的动画，这里可以有多个view对象进行动画
     * <p>
     * 在初始化view设置
     * ViewCompat.setTransitionName(iv_a,"options5");
     */
    public RxActivityOptionsTool screenTransitAnimByPair(Pair<View, String>... views) {
        this.options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context, views);
        return this;
    }
}
