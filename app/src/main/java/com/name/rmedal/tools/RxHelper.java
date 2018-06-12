package com.name.rmedal.tools;

import com.name.rmedal.api.HttpRespose;
import com.veni.tools.baserx.ServerException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
    public static <T> ObservableTransformer<HttpRespose<T>, T> handleResult() {
        return new ObservableTransformer<HttpRespose<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpRespose<T>> upstream) {
                return (ObservableSource<T>) upstream.flatMap(new Function<HttpRespose<T>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(HttpRespose<T> result) throws Exception {
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
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                try {
                    subscriber.onNext(data);
                    subscriber.onComplete();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });

    }
}
