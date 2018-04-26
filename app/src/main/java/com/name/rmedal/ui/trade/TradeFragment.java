package com.name.rmedal.ui.trade;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.name.rmedal.R;
import com.name.rmedal.base.BaseFragment;
import com.name.rmedal.modelbean.ModelDish;
import com.name.rmedal.modelbean.ModelDishMenu;
import com.name.rmedal.modelbean.ModelShopCart;
import com.veni.tools.StatusBarUtil;
import com.veni.tools.view.TitleView;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * 作者：kkan on 2018/2/24 14:41
 * 当前类注释:
 * 购物车
 */

public class TradeFragment extends BaseFragment{

    @BindView(R.id.trade_title_view)
    TitleView tradeTitleView;
    @BindView(R.id.shopping_cart_total_tv)
    TextView totalPriceTextView;
    @BindView(R.id.shopping_cart_bottom)
    LinearLayout mShoppingCartBottom;
    @BindView(R.id.left_menu)
    RecyclerView mLeftMenu;//左侧菜单栏
    @BindView(R.id.right_menu)
    RecyclerView mRightMenu;//右侧菜单栏
    @BindView(R.id.right_menu_tv)
    TextView headerView;
    @BindView(R.id.right_menu_item)
    LinearLayout headerLayout;//右侧菜单栏最上面的菜单
    @BindView(R.id.shopping_cart)
    ImageView mShoppingCart;
    @BindView(R.id.shopping_cart_layout)
    FrameLayout mShoppingCartLayout;
    @BindView(R.id.shopping_cart_total_num)
    TextView totalPriceNumTextView;
    @BindView(R.id.main_layout)
    RelativeLayout mMainLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trade;
    }

    @Override
    public void initPresenter() {

    }

    private ArrayList<ModelDishMenu> mModelDishMenuList;//数据源
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动
    private ModelShopCart mModelShopCart;
    @Override
    protected void initView(Bundle savedInstanceState) {
        StatusBarUtil.setPaddingSmart(context, tradeTitleView);
//        tradeTitleView.setLeftIconVisibility(false);
        tradeTitleView.setTitle("添加购物车");
        initData();
        initView();
    }

    private void initView() {

    }

    private void initData() {
        mModelShopCart = new ModelShopCart();
        mModelDishMenuList = new ArrayList<>();
        ArrayList<ModelDish> dishs1 = new ArrayList<>();
        dishs1.add(new ModelDish("面包", 1.0, 10));
        dishs1.add(new ModelDish("蛋挞", 1.0, 10));
        dishs1.add(new ModelDish("牛奶", 1.0, 10));
        dishs1.add(new ModelDish("肠粉", 1.0, 10));
        dishs1.add(new ModelDish("绿茶饼", 1.0, 10));
        dishs1.add(new ModelDish("花卷", 1.0, 10));
        dishs1.add(new ModelDish("包子", 1.0, 10));
        ModelDishMenu breakfast = new ModelDishMenu("早点", dishs1);

        ArrayList<ModelDish> dishs2 = new ArrayList<>();
        dishs2.add(new ModelDish("粥", 1.0, 10));
        dishs2.add(new ModelDish("炒饭", 1.0, 10));
        dishs2.add(new ModelDish("炒米粉", 1.0, 10));
        dishs2.add(new ModelDish("炒粿条", 1.0, 10));
        dishs2.add(new ModelDish("炒牛河", 1.0, 10));
        dishs2.add(new ModelDish("炒菜", 1.0, 10));
        ModelDishMenu launch = new ModelDishMenu("午餐", dishs2);

        ArrayList<ModelDish> dishs3 = new ArrayList<>();
        dishs3.add(new ModelDish("淋菜", 1.0, 10));
        dishs3.add(new ModelDish("川菜", 1.0, 10));
        dishs3.add(new ModelDish("湘菜", 1.0, 10));
        dishs3.add(new ModelDish("粤菜", 1.0, 10));
        dishs3.add(new ModelDish("赣菜", 1.0, 10));
        dishs3.add(new ModelDish("东北菜", 1.0, 10));
        ModelDishMenu evening = new ModelDishMenu("晚餐", dishs3);

        ArrayList<ModelDish> dishs4 = new ArrayList<>();
        dishs4.add(new ModelDish("淋菜", 1.0, 10));
        dishs4.add(new ModelDish("川菜", 1.0, 10));
        dishs4.add(new ModelDish("湘菜", 1.0, 10));
        dishs4.add(new ModelDish("湘菜", 1.0, 10));
        dishs4.add(new ModelDish("湘菜1", 1.0, 10));
        dishs4.add(new ModelDish("湘菜2", 1.0, 10));
        dishs4.add(new ModelDish("湘菜3", 1.0, 10));
        dishs4.add(new ModelDish("湘菜4", 1.0, 10));
        dishs4.add(new ModelDish("湘菜5", 1.0, 10));
        dishs4.add(new ModelDish("湘菜6", 1.0, 10));
        dishs4.add(new ModelDish("湘菜7", 1.0, 10));
        dishs4.add(new ModelDish("湘菜8", 1.0, 10));
        dishs4.add(new ModelDish("粤菜", 1.0, 10));
        dishs4.add(new ModelDish("赣菜", 1.0, 10));
        dishs4.add(new ModelDish("东北菜", 1.0, 10));
        ModelDishMenu menu1 = new ModelDishMenu("夜宵", dishs4);

        mModelDishMenuList.add(breakfast);
        mModelDishMenuList.add(launch);
        mModelDishMenuList.add(evening);
        mModelDishMenuList.add(menu1);
    }

}

