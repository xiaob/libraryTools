package com.veni.tools.baserx;

import android.content.Context;

import com.veni.tools.FutileTool;
import com.veni.tools.R;
import com.veni.tools.NetWorkTools;

import rx.Subscriber;

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
public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;

    public RxSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
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
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
