package com.name.rmedal.test;

import android.content.Context;
import android.os.Bundle;

import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.name.rmedal.modelbean.CardDataItem;
import com.name.rmedal.test.adapter.CardGroupAdapter;
import com.veni.tools.LogTools;
import com.veni.tools.StatusBarTools;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.view.TitleView;
import com.veni.tools.view.cardslide.CardSlidePanel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 */

public class CardGroupActivity extends BaseActivity {

    @BindView(R.id.cardgroupview_title_view)
    TitleView cardgroupviewTitleView;
    @BindView(R.id.cardgroupview_view)
    CardSlidePanel slidePanel;

    /**
     * 启动入口
     */
    public static void startAction(Context context) {
        new ActivityJumpOptionsTool().setContext(context)
                .setClass(CardGroupActivity.class)
                .customAnim()
                .start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cardgroupview;
    }

    @Override
    public void initPresenter() {

    }

    private CardSlidePanel.CardSwitchListener cardSwitchListener;
    private CardGroupAdapter cardGroupAdapter;
    private List<CardDataItem> dataList = new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
//        View view = cardgroupviewTitleView.getTvTitle();
//        ViewCompat.setTransitionName(view, AppConstant.TRANSITION_ANIMATION);
        StatusBarTools.immersive(this);
        StatusBarTools.setPaddingSmart(this, cardgroupviewTitleView);
        cardgroupviewTitleView.setLeftFinish(context);
        cardgroupviewTitleView.setTitle("拖拽式层叠卡片");
        setSwipeBackLayout(0);
        // 1. 左右滑动监听
        cardSwitchListener = new CardSlidePanel.CardSwitchListener() {

            @Override
            public void onShow(int index) {
                LogTools.e(TAG,"正在显示---"+index);
            }

            @Override
            public void onCardVanish(int index, int type) {
                LogTools.e(TAG,"正在消失---"+index+ " 消失type=" + type);
            }
        };
        slidePanel.setCardSwitchListener(cardSwitchListener);
        cardGroupAdapter=new CardGroupAdapter(context);
        slidePanel.setAdapter(cardGroupAdapter);
        addCard();
        addCard();
        Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        cardGroupAdapter.upData(dataList);
                    }
                });
    }

    private String imagePaths[] = {"file:///android_asset/wall01.jpg",
            "file:///android_asset/wall02.jpg", "file:///android_asset/wall03.jpg",
            "file:///android_asset/wall04.jpg", "file:///android_asset/wall05.jpg",
            "file:///android_asset/wall06.jpg", "file:///android_asset/wall07.jpg",
            "file:///android_asset/wall08.jpg", "file:///android_asset/wall09.jpg",
            "file:///android_asset/wall10.jpg", "file:///android_asset/wall11.jpg",
            "file:///android_asset/wall12.jpg"}; // 12个图片资源

    private String names[] = {"郭富城", "刘德华", "张学友", "李连杰", "成龙", "谢霆锋", "李易峰",
            "霍建华", "胡歌", "曾志伟", "吴孟达", "梁朝伟"}; // 12个人名
    private void addCard() {
        for(int i=0;i<12;i++){
            dataList.add(new CardDataItem(imagePaths[i],names[i]));
        }
    }


}
