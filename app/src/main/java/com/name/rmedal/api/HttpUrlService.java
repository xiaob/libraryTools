package com.name.rmedal.api;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * 作者：kkan on 2017/11/30
 * 当前类注释:
 * 请求地址
 */
public interface HttpUrlService {
    //版本更新
    @FormUrlEncoded
    @POST("system/getLastVersion")
//    Observable<HttpRespose<String>> getLastVersion(@FieldMap Map<String, String> map);
    Observable<HttpRespose<String>> getLastVersion(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

}
