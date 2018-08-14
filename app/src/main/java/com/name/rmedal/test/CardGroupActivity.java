package com.name.rmedal.test;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.name.rmedal.R;
import com.name.rmedal.base.BaseActivity;
import com.veni.tools.LogTools;
import com.veni.tools.StatusBarTools;
import com.veni.tools.base.ActivityJumpOptionsTool;
import com.veni.tools.view.CardGroupView;
import com.veni.tools.view.TitleView;

import butterknife.BindView;

/**
 * 作者：kkan on 2018/04/20
 * 当前类注释:
 */

public class CardGroupActivity extends BaseActivity {

    @BindView(R.id.cardgroupview_title_view)
    TitleView cardgroupviewTitleView;
    @BindView(R.id.cardgroupview_view)
    CardGroupView cardgroupviewView;

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


    @Override
    public void initView(Bundle savedInstanceState) {
//        View view = cardgroupviewTitleView.getTvTitle();
//        ViewCompat.setTransitionName(view, AppConstant.TRANSITION_ANIMATION);
        StatusBarTools.immersive(this);
        StatusBarTools.setPaddingSmart(this, cardgroupviewTitleView);
        cardgroupviewTitleView.setLeftFinish(context);
        cardgroupviewTitleView.setTitle("拖拽式层叠卡片");
//        setSwipeBackLayout(0);
        cardgroupviewView.setLeftOrRightListener(new CardGroupView.LeftOrRight() {
            @Override
            public void leftOrRight(boolean left) {
                LogTools.e(TAG,"left---"+left);
            }
        });
        cardgroupviewView.setLoadMoreListener(new CardGroupView.LoadMore() {
            @Override
            public void load() {
                LogTools.e(TAG,"LoadMore---");
                addCard();
            }
        });
        cardgroupviewView.setLoadSize(2);//当剩余卡片等于size时，加载更多
        addCard();
    }
    private void addCard() {
        cardgroupviewView.addView(getCard());
        cardgroupviewView.addView(getCard());
        cardgroupviewView.addView(getCard());
        cardgroupviewView.addView(getCard());
        cardgroupviewView.addView(getCard());
        cardgroupviewView.addView(getCard());

    }
    int cardnum=0;
    private View getCard() {
        View card = LayoutInflater.from(this).inflate(R.layout.activity_cardgroupview_item_card, null);
        View view = card.findViewById(R.id.item_card_rightbtn);
        TextView item_card_mess = card.findViewById(R.id.item_card_mess);
        cardnum++;
        item_card_mess.setText(""+cardnum);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardgroupviewView.removeTopCard(true);
            }
        });
        return card;
    }
}
