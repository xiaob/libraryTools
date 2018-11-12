package com.name.rmedal.api;


import com.name.rmedal.modelbean.PersonalModelBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 作者：kkan on 2017/11/30
 * 当前类注释:
 * 请求地址
 *
 * 表单的方式传递键值对@FormUrlEncoded
 * 单文件上传@Multipart  例 @Part MultipartBody.Part photo
 * 多文件上传@PartMap 例 registerUser(@PartMap Map<String, RequestBody> params,  @Part("password") RequestBody password);
 */
public interface HttpUrlService {
    //版本更新
    @FormUrlEncoded
    @POST("system/getLastVersion")
    Observable<HttpRespose<List<PersonalModelBean>>> getLastVersion(@FieldMap Map<String, String> map);
//    Observable<HttpRespose<String>> getLastVersion(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

}
