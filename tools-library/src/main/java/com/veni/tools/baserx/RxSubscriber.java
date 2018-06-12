package com.veni.tools.baserx;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.veni.tools.FutileTool;
import com.veni.tools.LogTools;
import com.veni.tools.R;
import com.veni.tools.NetWorkTools;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 作者：kkan on 2017/01/30
 * 当前类注释:
 * 订阅封装
 * <p>
 * ----------------使用例子----------------*
 * _apiService.login(mobile, verifyCode)
 * .//省略
 * .subscribe(new RxSubscriber<User user>(mContext,false) {
 *
 * public void _onNext(User user) {
 * // 处理user
 * }
 * public void _onError(String msg) {
 * RxToast.error(mActivity, msg);
 * });
 */
public abstract class RxSubscriber<T> implements Observer<T> {

    private Context mContext;

    public RxSubscriber(Context context) {
        this.mContext = context;
    }

    protected String errMsg = "";
    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        LogTools.e("Observer.java", e.getMessage() + "");
//
//        if (!NetworkUtils.isConnected()) {
//            errMsg = "网络连接出错,";
//        } else if (e instanceof HttpException) {
//            errMsg = "网络请求出错,";
//        } else if (e instanceof IOException) {
//            errMsg = "网络出错,";
//        }
//网络
        if (!NetWorkTools.isAvailable(FutileTool.getContext())) {
            _onError(FutileTool.getContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
        //其它
        else {
            _onError(FutileTool.getContext().getString(R.string.net_error));
        }
        disposeIt();
    }

    @Override
    public void onComplete() {
        disposeIt();
    }

    /**
     * 销毁disposable
     */
    private void disposeIt() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
            disposable = null;
        }
    }


    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
