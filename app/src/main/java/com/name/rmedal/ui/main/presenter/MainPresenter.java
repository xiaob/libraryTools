package com.name.rmedal.ui.main.presenter;


import com.name.rmedal.R;
import com.name.rmedal.api.HttpManager;
import com.name.rmedal.base.HttpRespose;
import com.name.rmedal.modelbean.PersonalModelBean;
import com.name.rmedal.ui.main.contract.MainContract;
import com.name.rmedal.base.RxSubscriber;
import com.veni.tools.baserx.RxSchedulers;

import java.util.HashMap;
import java.util.List;

/**
 * 作者：Administrator on 2017/12/04 10:36
 * 当前类注释:
 */
public class MainPresenter extends MainContract.Presenter {

    @Override
    public void checkVersion(String type) {
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
                    public void _onError(int code, String message) {
                        mView.onError(code, message);
                    }
                });
    }
}
