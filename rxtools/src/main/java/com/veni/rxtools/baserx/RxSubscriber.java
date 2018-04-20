package com.veni.rxtools.baserx;

import android.content.Context;

import com.veni.rxtools.R;
import com.veni.rxtools.RxNetTool;
import com.veni.rxtools.RxTool;

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
    private String msg;

    public RxSubscriber(Context context, String msg) {
        this.mContext = context;
        this.msg = msg;
    }

    public RxSubscriber(Context context) {
        this(context, RxTool.getContext().getString(R.string.loading));
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
        if (!RxNetTool.isAvailable(RxTool.getContext())) {
            _onError(RxTool.getContext().getString(R.string.no_net));
        }
        //服务器
        else if (e instanceof ServerException) {
            _onError(e.getMessage());
        }
        //其它
        else {
            _onError(RxTool.getContext().getString(R.string.net_error));
        }
    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);

}
