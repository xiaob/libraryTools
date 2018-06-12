package com.name.rmedal.ui.main.contract;


import com.veni.tools.base.BaseModel;
import com.veni.tools.base.BasePresenter;
import com.veni.tools.base.BaseView;

import io.reactivex.Observable;


/**
 * 作者：Administrator on 2017/12/04 10:36
 * 当前类注释:
 * MVP契约类
 */
public interface MainContract {
    /**
     * 请求数据   数据相关
     */
    interface Model extends BaseModel {
        //检测更新
        Observable<String> checkVersion(String type);
    }

    /**
     * 向 页面 返回数据
     *  页面中实现这个  View 接口
     */
    interface View extends BaseView {
        //注册返回的数据
        void returnVersionData(String data);
    }
    /**
     * 发起请求
     * 继承这个抽象类
     *   调用Model获取网络数据，用View中的接口  更新界面
     */
    abstract static class Presenter extends BasePresenter<View, Model> {
        //发起注册请求
        public abstract void checkVersion(String type);
    }

}
