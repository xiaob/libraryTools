package com.name.rmedal.test.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.name.rmedal.R;
import com.name.rmedal.bigimage.BigImageBean;
import com.name.rmedal.bigimage.BigImagePagerActivity;
import com.name.rmedal.modelbean.CardDataItem;
import com.veni.tools.interfaces.OnNoFastClickListener;
import com.veni.tools.view.itoast.ToastTool;
import com.veni.tools.view.cardslide.CardAdapter;
import com.veni.tools.view.imageload.ImageLoaderTool;

import java.util.ArrayList;
import java.util.List;

/**
 * 层叠卡片的Adapter
 */
public class CardGroupAdapter extends CardAdapter {

    private List<CardDataItem> dataList;
    private Context context;

    public CardGroupAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }
    public void upData(List<CardDataItem>dataList ){
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_cardgroupview_item_card;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int index) {
        return dataList.get(index);
    }

    @Override
    public void bindView(View view, int index) {
        Object tag = view.getTag();
        ViewHolder viewHolder;
        if (null != tag) {
            viewHolder = (ViewHolder) tag;
        } else {
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        viewHolder.bindData(dataList.get(index));
    }

    class ViewHolder {
        ImageView imageView;
        View maskView;
        TextView userNameTv;
        TextView imageNumTv;
        TextView likeNumTv;
        TextView cardOtherDescription;
        View contentView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.card_image_view);
            maskView = view.findViewById(R.id.maskView);
            userNameTv = view.findViewById(R.id.card_user_name);
            imageNumTv = view.findViewById(R.id.card_pic_num);
            likeNumTv = view.findViewById(R.id.card_like);
            cardOtherDescription = view.findViewById(R.id.card_other_description);
            contentView = view.findViewById(R.id.card_item_content);
        }

        public void bindData(final CardDataItem itemData) {
            ImageLoaderTool.with(context).loadUrl(itemData.getImagePath()).into(imageView);
            userNameTv.setText(itemData.getUserName());
            imageNumTv.setText(itemData.getImageNum() + "");
            likeNumTv.setText(itemData.getLikeNum() + "");
            contentView.setOnClickListener(new OnNoFastClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    ToastTool.normal(""+itemData.getUserName());
                }
            });

            maskView.setOnClickListener(new OnNoFastClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    //放大查看图片
                    List<BigImageBean> img_list = new ArrayList<>();
                    img_list.add(new BigImageBean(itemData.getImagePath()
                            , ""));
                    BigImagePagerActivity.startAction(context, img_list, 0);
                }
            });
        }
    }

    @Override
    public Rect obtainDraggableArea(View view) {
        // 可滑动区域定制，该函数只会调用一次
        View contentView = view.findViewById(R.id.card_item_content);
        View topLayout = view.findViewById(R.id.card_top_layout);
        View bottomLayout = view.findViewById(R.id.card_bottom_layout);
        int left = view.getLeft() + contentView.getPaddingLeft() + topLayout.getPaddingLeft();
        int right = view.getRight() - contentView.getPaddingRight() - topLayout.getPaddingRight();
        int top = view.getTop() + contentView.getPaddingTop() + topLayout.getPaddingTop();
        int bottom = view.getBottom() - contentView.getPaddingBottom() - bottomLayout.getPaddingBottom();
        return new Rect(left, top, right, bottom);
    }
}
