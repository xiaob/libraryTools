package com.name.rmedal.tools;

import com.name.rmedal.api.HttpRespose;
import com.veni.tools.baserx.ServerException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 作者：kkan on 2017/01/30
 * 当前类注释:
 *      对服务器返回数据成功和失败处理
 */

/**************使用例子******************/
/*_apiService.login(mobile, verifyCode)
        .compose(RxSchedulersHelper.io_main())
        .compose(RxResultHelper.handleResult())
        .//省略*/

public class RxHelper {
    /**
     * 对服务器返回数据进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<HttpRespose<T>, T> handleResult() {
        return new Observable.Transformer<HttpRespose<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpRespose<T>> tObservable) {
                return tObservable.flatMap(new Func1<HttpRespose<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpRespose<T> result) {
                        if (result.success()) {
                            return createData(result.data);
                        } else {
                            return Observable.error(new ServerException(result.msg));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };

    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }
}
