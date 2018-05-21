package com.veni.tools.baserx;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：kkan on 2018/05/21
 * 当前类注释:
 * 公共参数拦截器
 */

public class BasicParamsInterceptor implements Interceptor {
    private Map<String, String> mHeaderParamsMap = new HashMap<>();

    public BasicParamsInterceptor() {
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Log.d("HttpCommonInterceptor", "add common params");
        Request oldRequest = chain.request();
        // 添加新的参数，添加到url 中
     /* HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()                .newBuilder()
         .scheme(oldRequest.url().scheme())
             .host(oldRequest.url().host());*/
        // 新的请求
        Request.Builder requestBuilder = oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(),
                oldRequest.body());

        //添加公共参数,添加到header中
        if (mHeaderParamsMap.size() > 0) {
            for (Map.Entry<String, String> params : mHeaderParamsMap.entrySet()) {
                requestBuilder.header(params.getKey(), params.getValue());
            }
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }

    public static class Builder {
        BasicParamsInterceptor mHttpCommonInterceptor;

        public Builder() {
            mHttpCommonInterceptor = new BasicParamsInterceptor();
        }

        public Builder addHeaderParams(String key, String value) {
            mHttpCommonInterceptor.mHeaderParamsMap.put(key, value);
            return this;
        }

        public Builder addHeaderParams(String key, int value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, float value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, long value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public Builder addHeaderParams(String key, double value) {
            return addHeaderParams(key, String.valueOf(value));
        }

        public BasicParamsInterceptor build() {
            return mHttpCommonInterceptor;
        }
    }
}
