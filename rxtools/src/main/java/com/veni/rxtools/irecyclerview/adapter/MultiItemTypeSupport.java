package com.veni.rxtools.irecyclerview.adapter;

public interface MultiItemTypeSupport<T>{
	int getLayoutId(int itemType);

	int getItemViewType(int position, T t);
}