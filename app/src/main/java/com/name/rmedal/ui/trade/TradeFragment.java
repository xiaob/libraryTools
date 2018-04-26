package com.name.rmedal.ui.trade;

import android.os.Bundle;
import android.view.View;

import com.name.rmedal.R;
import com.name.rmedal.base.BaseFragment;
import com.veni.tools.StatusBarTools;
import com.veni.tools.view.ShoppingView;
import com.veni.tools.view.TitleView;
import com.veni.tools.view.ticker.TickerUtils;
import com.veni.tools.view.ticker.TickerView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 作者：kkan on 2018/2/24 14:41
 * 当前类注释:
 * 购物车
 */

public class TradeFragment extends BaseFragment{

    @BindView(R.id.trade_title_view)
    TitleView tradeTitleView;
    @BindView(R.id.trade_sv_1)
    ShoppingView tradeSv1;
    @BindView(R.id.trade_made_count)
    TickerView tradeMadeCount;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trade;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        StatusBarTools.setPaddingSmart(context, tradeTitleView);
        tradeTitleView.setTitle("添加购物车");

        tradeMadeCount.setCharacterList(TickerUtils.getDefaultNumberList());
        tradeMadeCount.setText("￥0.0", true);
        tradeSv1.setOnShoppingClickListener(new ShoppingView.ShoppingClickListener() {
            @Override
            public void onAddClick(int num) {
                tradeMadeCount.setText("数量："+num, true);
            }

            @Override
            public void onMinusClick(int num) {
                tradeMadeCount.setText("数量："+num, true);
            }
        });
    }


    @OnClick({R.id.trade_made_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.trade_made_count:
                tradeSv1.setTextNum(1);
                break;
        }
    }
}

