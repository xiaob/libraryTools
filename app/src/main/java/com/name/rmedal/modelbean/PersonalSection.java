package com.name.rmedal.modelbean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class PersonalSection extends SectionEntity<PersonalModelBean> {
    private boolean isMore;
    private int itemType;
    private PersonalModelBean personalitem;
    public PersonalSection(boolean isHeader, String header, boolean isMroe) {
        super(isHeader, header);
        this.isMore = isMroe;
    }

    public PersonalSection(PersonalModelBean personalitem) {
        super(personalitem);
        this.personalitem = personalitem;
    }

    public boolean isMore() {
        return isMore;
    }

    public void setMore(boolean mroe) {
        isMore = mroe;
    }

    public PersonalModelBean getPersonalitem() {
        return personalitem;
    }

    public void setPersonalitem(PersonalModelBean personalitem) {
        this.personalitem = personalitem;
    }
}
