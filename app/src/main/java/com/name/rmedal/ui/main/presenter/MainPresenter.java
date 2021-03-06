package com.name.rmedal.ui.main.presenter;


import com.name.rmedal.R;
import com.name.rmedal.api.HttpManager;
import com.name.rmedal.api.HttpRespose;
import com.name.rmedal.modelbean.PersonalModelBean;
import com.name.rmedal.ui.main.contract.MainContract;
import com.name.rmedal.api.RxSubscriber;
import com.veni.tools.baserx.RxSchedulers;
import com.veni.tools.view.itoast.ToastTool;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Administrator on 2017/12/04 10:36
 * 当前类注释:
 * Presenter
 */
public class MainPresenter extends MainContract.Presenter {

    @Override
    public void checkVersion(String type) {
        //请求参数
        HashMap<String, String> param = new HashMap<>();
        param.put("type", type);
        HttpManager.getInstance().getOkHttpUrlService().getLastVersion(param)
                .compose(RxSchedulers.<HttpRespose<List<PersonalModelBean>>>io_main())
                .subscribe(new RxSubscriber<List<PersonalModelBean>>(mContext, mContext.getString(R.string.loading)) {
                    @Override
                    public void _onNext(List<PersonalModelBean> data) {
                        mView.returnVersionData(data);
                    }

                    @Override
                    public void onErrorSuccess(int code, String message, boolean issuccess) {
                        mView.onErrorSuccess(code, message, issuccess,true);
                    }

                });
    }
}
