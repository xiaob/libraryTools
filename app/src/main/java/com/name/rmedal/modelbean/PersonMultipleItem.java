package com.name.rmedal.modelbean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionMultiEntity;

/**
 * 作者：kkan on 2018/10/25 13:27
 * <p>
 * 当前类注释:
 */
public class PersonMultipleItem extends SectionMultiEntity<PersonalModelBean> implements MultiItemEntity {

    public static final int PERDONALPHONE = 1;
    private int itemType;
    private PersonalModelBean personalitem;

    public PersonMultipleItem(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public PersonMultipleItem(int itemType, PersonalModelBean personalitem) {
        super(personalitem);
        this.personalitem = personalitem;
        this.itemType = itemType;
    }

    public PersonalModelBean getPersonalitem() {
        return personalitem;
    }

    public void setPersonalitem(PersonalModelBean personalitem) {
        this.personalitem = personalitem;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
