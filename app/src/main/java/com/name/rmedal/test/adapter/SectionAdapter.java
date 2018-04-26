package com.name.rmedal.test.adapter;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.name.rmedal.R;
import com.name.rmedal.modelbean.ModelContactCity;
import com.name.rmedal.modelbean.MySection;

import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionAdapter extends BaseSectionQuickAdapter<MySection, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param layoutResId      The layout resource id of each item.
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public SectionAdapter(int layoutResId, int sectionHeadResId, List<MySection> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, final MySection item) {
        helper.setText(R.id.city_tip, item.header);
    }


    @Override
    protected void convert(BaseViewHolder helper, MySection item) {
        ModelContactCity video = (ModelContactCity) item.t;
        helper.setText(R.id.tv_contact_name, video.getName());
    }

    public int getLetterPosition(String letter){
        for (int i = 0 ; i < getData().size(); i++){
            MySection mys= getData().get(i);
            if(mys.isHeader&& mys.header.equals(letter)){
                return i;
            }
        }
        return -1;
    }
}
