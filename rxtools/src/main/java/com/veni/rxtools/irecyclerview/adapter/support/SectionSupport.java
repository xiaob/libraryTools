package com.veni.rxtools.irecyclerview.adapter.support;

/**
 * 添加分类Header
 * Created by xiyn on 16/4/9.
 */
public interface SectionSupport<T>{
    public int sectionHeaderLayoutId();

    public int sectionTitleTextViewId();

    public String getTitle(T t);
}
