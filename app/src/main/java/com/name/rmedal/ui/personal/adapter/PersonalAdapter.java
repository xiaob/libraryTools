package com.name.rmedal.ui.personal.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseSectionMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.name.rmedal.R;
import com.name.rmedal.modelbean.PersonMultipleItem;
import com.name.rmedal.modelbean.PersonalModelBean;

import java.util.List;

/**
 * 作者：kkan on 2018/10/25 9:30
 * <p>
 * 当前类注释:
 */
public class PersonalAdapter extends BaseSectionMultiItemQuickAdapter<PersonMultipleItem, BaseViewHolder> {
    private Context context;
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    public PersonalAdapter(int sectionHeadResId, List<PersonMultipleItem> data) {
        super(sectionHeadResId, data);
        this.context=context;
        addItemType(PersonMultipleItem.PERDONALPHONE, R.layout.fragment_persional_item_wave_contact);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, PersonMultipleItem item) {
        helper.setText(R.id.city_tip, item.header);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PersonMultipleItem item) {
        // deal with multiple type items viewHolder
        viewHolder.addOnClickListener(R.id.persional_item_ll);
        switch (viewHolder.getItemViewType()) {
            case PersonMultipleItem.PERDONALPHONE:
                PersonalModelBean personalitem =item.getPersonalitem();
                viewHolder.setText(R.id.tv_contact_name, personalitem.getName());
                break;

        }
    }

    public int getLetterPosition(String letter){
        for (int i = 0 ; i < getData().size(); i++){
            PersonMultipleItem mys= getData().get(i);
            if(mys.isHeader&& mys.header.equals(letter)){
                return i;
            }
        }
        return -1;
    }

}
