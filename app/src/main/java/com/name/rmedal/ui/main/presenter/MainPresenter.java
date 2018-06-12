package com.name.rmedal.ui.main.presenter;


import com.name.rmedal.R;
import com.name.rmedal.tools.AppTools;
import com.name.rmedal.ui.main.contract.MainContract;
import com.veni.tools.baserx.RxSubscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 作者：Administrator on 2017/12/04 10:36
 * 当前类注释:
 */
public class MainPresenter extends MainContract.Presenter {

    @Override
    public void checkVersion(String type) {

        mModel.checkVersion(type).subscribe(new RxSubscriber<String>(mContext) {

            @Override
            public void onSubscribe(Disposable d) {
                super.onSubscribe(d);
                mRxManage.add(d);
            }
            //
//            @Override
//            public void onStart() {
//                super.onStart();
//                mView.showLoading(mContext.getString(R.string.loading));
//            }
//
            @Override
            protected void _onNext(String datastr) {
                String data = AppTools.desAESCode(datastr);
                mView.returnVersionData(data);
                mView.stopLoading();
            }

            @Override
            protected void _onError(String message) {
                mView.showErrorTip(message);
            }
        });
    }
}
