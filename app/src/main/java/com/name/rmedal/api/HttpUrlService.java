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
    //用户登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpRespose<String>> login(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //获得文章相关
    @FormUrlEncoded
    @POST("article/list")
    Observable<HttpRespose<String>> getArticleData(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //获得文章详情
    @FormUrlEncoded
    @POST("article/detail")
    Observable<HttpRespose<String>> getArticleDetail(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //获得验证码
    @FormUrlEncoded
    @POST("user/getCheckCode")
    Observable<HttpRespose<String>> getCheckCode(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //注册用户
    @FormUrlEncoded
    @POST("user/register")
    Observable<HttpRespose<String>> register(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //获得用户信息
    @FormUrlEncoded
    @POST("user/findByUserId")
    Observable<HttpRespose<String>> getUserInfo(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //查询地区列表
    @FormUrlEncoded
    @POST("area/list")
    Observable<HttpRespose<String>> getAreaList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //校验验证码
    @FormUrlEncoded
    @POST("user/checkVerfiyCode")
    Observable<HttpRespose<String>> checkCode(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //通过手机获取用户id
    @FormUrlEncoded
    @POST("user/findByPhone")
    Observable<HttpRespose<String>> getUserIdForPhone(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //修改密码
    @FormUrlEncoded
    @POST("user/updatePassword")
    Observable<HttpRespose<String>> changePwd(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    @FormUrlEncoded
    @POST("user/updatePhone")
    Observable<HttpRespose<String>> updatePhone(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    @FormUrlEncoded
    @POST("order/list")
    Observable<HttpRespose<String>> getOrderList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //采购界面分类
    @FormUrlEncoded
    @POST("goods/findCatalogByParentId")
    Observable<HttpRespose<String>> getCategory(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //商品搜索
    @FormUrlEncoded
    @POST("goods/list")
    Observable<HttpRespose<String>> getGoodsList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //商品详情
    @FormUrlEncoded
    @POST("goods/detail")
    Observable<HttpRespose<String>> getGoodDetail(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //添加购物车
    @FormUrlEncoded
    @POST("cart/addShopCart")
    Observable<HttpRespose<String>> addCart(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //立即购买
    @FormUrlEncoded
    @POST("order/buyNowCommitOrder")
    Observable<HttpRespose<String>> buyNow(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //提交订单(购物车)
    @FormUrlEncoded
    @POST("order/commitOrder")
    Observable<HttpRespose<String>> commitOrder(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //购物车列表
    @FormUrlEncoded
    @POST("cart/goCart")
    Observable<HttpRespose<String>> getCartList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //购物车删除商品
    @FormUrlEncoded
    @POST("cart/deleteCartProduct")
    Observable<HttpRespose<String>> deleteProductForCart(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //更新购物车数量
    @FormUrlEncoded
    @POST("cart/updateShopCart")
    Observable<HttpRespose<String>> updateProductForCart(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //采购首页
    @FormUrlEncoded
    @POST("goods/index")
    Observable<HttpRespose<String>> procurementIndex(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);
    //采购首页
    @FormUrlEncoded
    @POST("field/testAes")
    Observable<HttpRespose<String>> testAes(@Field("data") String data);

    //首页加载更多
    @FormUrlEncoded
    @POST("goods/indexList")
    Observable<HttpRespose<String>> loadMoreProcurement(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //我的界面订单详情
    @FormUrlEncoded
    @POST("order/detail")
    Observable<HttpRespose<String>> getMyOrderDetail(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //删除订单
    @FormUrlEncoded
    @POST("order/deleteOrder")
    Observable<HttpRespose<String>> deleteOrder(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //取消订单
    @FormUrlEncoded
    @POST("order/cancelOrder")
    Observable<HttpRespose<String>> cancelOrder(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //退出登录
    @FormUrlEncoded
    @POST("user/exit")
    Observable<HttpRespose<String>> logout(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //更新订单状态
    @FormUrlEncoded
    @POST("order/updateStatusOrder")
    Observable<HttpRespose<String>> updateStatusOrder(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    /**
     * 更新用户信息
     */
    @Multipart
    @POST("user/updateUser")
    Observable<HttpRespose<String>> updateUser(@PartMap Map<String, RequestBody> params);


    //是否加入合作社
    @FormUrlEncoded
    @POST("user_union/whetherUserUnion")
    Observable<HttpRespose<String>> isUnion(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //查询合作社
    @FormUrlEncoded
    @POST("user_union/detailUserUnion")
    Observable<HttpRespose<String>> getDetailUserUnion(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //创建保存更新合作社
    @Multipart
    @POST("user_union/saveOrUpdateUserUnion")
    Observable<HttpRespose<String>> createOrSaveUnion(@PartMap Map<String, RequestBody> params);


    //种植信息中查询类型
    @FormUrlEncoded
    @POST("system/findSysDictByType")
    Observable<HttpRespose<String>> findSysDictByType(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //查询种植信息
    @FormUrlEncoded
    @POST("user/detailUserPlat")
    Observable<HttpRespose<String>> getPlantInfo(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //更新种植信息
    @FormUrlEncoded
    @POST("user/saveOrUpdateUserPlat")
    Observable<HttpRespose<String>> updatePlantInfo(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //添加和更新合作社成员
    @FormUrlEncoded
    @POST("user_union/saveOrUpdateUnionUsers")
    Observable<HttpRespose<String>> addUnionMember(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //查询合作社用户列表
    @FormUrlEncoded
    @POST("user_union/findUnionUsers")
    Observable<HttpRespose<String>> getUnionUserList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //获得钱包信息
    @FormUrlEncoded
    @POST("wallet/findWalletDetail")
    Observable<HttpRespose<String>> findWalletDetail(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //是否获得植保计划
    @FormUrlEncoded
    @POST("plat/whetherPlan")
    Observable<HttpRespose<String>> isZhibao(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //申请植保计划
    @FormUrlEncoded
    @POST("plat/applyPlan")
    Observable<HttpRespose<String>> applyPlan(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //植保计划记录
    @FormUrlEncoded
    @POST("plat/findUserPlatRecord")
    Observable<HttpRespose<String>> findUserPlatRecord(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //验证合作社成员验证码
    @FormUrlEncoded
    @POST("user_union/checkUserVerfiyCode")
    Observable<HttpRespose<String>> checkUnionVerfiyCode(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //查询合作社和个人钱包交易记录
    @FormUrlEncoded
    @POST("wallet/findWalletLogs")
    Observable<HttpRespose<String>> findWalletLogs(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //更新植保记录
    @Multipart
    @POST("plat/updateUserPlatRecord")
    Observable<HttpRespose<String>> saveOrUpdateUserUnion(@PartMap Map<String, RequestBody> params);

    //获得支付签名
    @FormUrlEncoded
    @POST("pay/getPaySign")
    Observable<HttpRespose<String>> getPaySign(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //获得支付签名
    @FormUrlEncoded
    @POST("pay/getFieldPaySign")
    Observable<HttpRespose<String>> getFieldPaySign(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //充值
    @FormUrlEncoded
    @POST("pay/creteOnlineRecharge")
    Observable<HttpRespose<String>> OnlineRecharge(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //个人植保信息是否填写完整
    @FormUrlEncoded
    @POST("user/whetherFillPlatInfo")
    Observable<HttpRespose<String>> whetherFillPlatInfo(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //个人植保信息是否填写完整
    @FormUrlEncoded
    @POST("user_union/updateBatchUnionUsers")
    Observable<HttpRespose<String>> updateBatchUnionUsers(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //查看当前用户在合作社中的身份状态
    @FormUrlEncoded
    @POST("user_union/findUnionUserByUserId")
    Observable<HttpRespose<String>> findUnionUserByUserId(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //更新支付密码
    @FormUrlEncoded
    @POST("wallet/updatePayPassword")
    Observable<HttpRespose<String>> updatePayPassword(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //检查是否存在支付密码
    @FormUrlEncoded
    @POST("wallet/whetherPayPassword")
    Observable<HttpRespose<String>> whetherPayPassword(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //支付密码直接支付
    @FormUrlEncoded
    @POST("wallet/validatePayPassword")
    Observable<HttpRespose<String>> validatePayPassword(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //个人钱包支付
    @FormUrlEncoded
    @POST("pay/walletPay")
    Observable<HttpRespose<String>> walletPay(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //薯田钱包支付
    @FormUrlEncoded
    @POST("pay/fieldWalletPay")
    Observable<HttpRespose<String>> fieldWalletPay(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //添加或修改收货地址
    @FormUrlEncoded
    @POST("address/saveOrUpdateUserAddress")
    Observable<HttpRespose<String>> saveOrUpdateUserAddress(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //查询收货地址列表
    @FormUrlEncoded
    @POST("address/list")
    Observable<HttpRespose<String>> queryAddrList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //查询收货地址详情
    @FormUrlEncoded
    @POST("address/detail")
    Observable<HttpRespose<String>> queryAddrDetail(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //删除地址
    @FormUrlEncoded
    @POST("address/delete")
    Observable<HttpRespose<String>> deleteAddress(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //线下充值
    @FormUrlEncoded
    @POST("pay/createUnionRemitRechargeOrder")
    Observable<HttpRespose<String>> createUnionRemitRechargeOrder(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //删除地址
    @FormUrlEncoded
    @POST("pay/sendUnionRemitRechargeMessage")
    Observable<HttpRespose<String>> sendUnionRemitRechargeMessage(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //版本更新
    @FormUrlEncoded
    @POST("system/getLastVersion")
    Observable<HttpRespose<String>> getLastVersion(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //检查用户是否存在
    @FormUrlEncoded
    @POST("user/checkPhoneExist")
    Observable<HttpRespose<String>> checkPhoneExist(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //查询服务站id
    @FormUrlEncoded
    @POST("station/findStationByUserId")
    Observable<HttpRespose<String>> findStationByUserId(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //综合查询植保
    @FormUrlEncoded
    @POST("plat/findUserPlatRecordBySearchVO")
    Observable<HttpRespose<String>> queryZhibao(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //快捷评分列表
    @FormUrlEncoded
    @POST("station/findCooperationList")
    Observable<HttpRespose<String>> getFastScoreList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //退出联合体
    @FormUrlEncoded
    @POST("user_union/exitUserUnion")
    Observable<HttpRespose<String>> exitUserUnion(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //获得服务站下合作社列表
    @FormUrlEncoded
    @POST("user_union/list")
    Observable<HttpRespose<String>> getServicePointUnionList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //查询合作社下有哪些接口
    @FormUrlEncoded
    @POST("order/unionOrders")
    Observable<HttpRespose<String>> queryUnionProduct(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //合作社植保计划界面接口
    @FormUrlEncoded
    @POST("plat/findUnionCurrUserPlatRecordStat")
    Observable<HttpRespose<String>> findUnionCurrUserPlatRecordStat(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //植保记录添加用品接口
    @Multipart
    @POST("plat/saveOrUpdateUserPlatGoods")
    Observable<HttpRespose<String>> addProduct(@PartMap Map<String, RequestBody> params);


    //删除植保记录中商品
    @FormUrlEncoded
    @POST("plat/deleteUserPlatGoodsById")
    Observable<HttpRespose<String>> deleteUserPlatGoodsById(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //查看记录详情
    @FormUrlEncoded
    @POST("plat/findUserPlatRecordDetail")
    Observable<HttpRespose<String>> findUserPlatRecordDetail(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //撤销植保记录
    @FormUrlEncoded
    @POST("plat/unDoUserPlatRecord")
    Observable<HttpRespose<String>> unDoUserPlatRecord(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    // 服务站人员评分
    @Multipart
    @POST("plat/commentUserPlatRecord")
    Observable<HttpRespose<String>> commentUserPlatRecord(@PartMap Map<String, RequestBody> params);


    //查询服务站下有订单的合作社
    @FormUrlEncoded
    @POST("user_union/findUserUnionListByStationId")
    Observable<HttpRespose<String>> findUserUnionListByStationId(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //土豆圈首页数据
    @FormUrlEncoded
    @POST("circle/selectList")
    Observable<HttpRespose<String>> selectList(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //土豆圈点赞
    @FormUrlEncoded
    @POST("circle/insertPraise")
    Observable<HttpRespose<String>> zan(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //取消点赞
    @FormUrlEncoded
    @POST("circle/deletePraise")
    Observable<HttpRespose<String>> cancelZan(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //土豆圈合作社详情
    @FormUrlEncoded
    @POST("circle/detailUserUnion")
    Observable<HttpRespose<String>> detailUserUnion(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //土豆圈地块详情
    @FormUrlEncoded
    @POST("circle/detailUser")
    Observable<HttpRespose<String>> detailDikuai(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //评论接口
    @FormUrlEncoded
    @POST("circle/commentOrReply")
    Observable<HttpRespose<String>> commentOrReply(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //发布土豆圈
    @FormUrlEncoded
    @POST("plat/publishOrCancelPublish")
    Observable<HttpRespose<String>> publishOrCancelPublish(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //设置朋友圈显示条数
    @FormUrlEncoded
    @POST("user/setPageSize")
    Observable<HttpRespose<String>> setPageSize(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    //收藏
    @FormUrlEncoded
    @POST("circle/savePlatCollection")
    Observable<HttpRespose<String>> collection(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //取消收藏
    @FormUrlEncoded
    @POST("circle/deletePlatCollection")
    Observable<HttpRespose<String>> unCollection(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //查询土豆圈设置动态显示条数
    @FormUrlEncoded
    @POST("user/findPageSize")
    Observable<HttpRespose<String>> findPageSize(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //综合查询薯田列表
    @GET("field/selectFieldList")
    Observable<HttpRespose<String>> selectFieldList(@Query("data") String data, @Query("deviceToken") String deviceToken, @Query("version") String version);

    //查询薯田相关图文信息
    @GET("field/selectGraphicAndText")
    Observable<HttpRespose<String>> selectGraphicAndText(@Query("data") String data, @Query("deviceToken") String deviceToken, @Query("version") String version);

    //查询薯田农资使用信息
    @GET("field/selectFieldPlatGoods")
    Observable<HttpRespose<String>> selectFieldPlatGoods(@Query("data") String data, @Query("deviceToken") String deviceToken, @Query("version") String version);


    //查询薯田农资使用信息
    @GET("field/selectSpecInfoList")
    Observable<HttpRespose<String>> selectSpecInfoList(@Query("data") String data, @Query("deviceToken") String deviceToken, @Query("version") String version);


    //提交预约信息
    @FormUrlEncoded
    @POST("field/toReserve")
    Observable<HttpRespose<String>> toReserve(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);

    //查询薯田农资使用信息
    @GET("field/selectReservationList")
    Observable<HttpRespose<String>> selectReservationList(@Query("data") String data, @Query("deviceToken") String deviceToken, @Query("version") String version);



    //查询薯田植保记录信息
    @GET("field/selectFieldPlatRecordList")
    Observable<HttpRespose<String>> selectFieldPlatRecordList(@Query("data") String data, @Query("deviceToken") String deviceToken, @Query("version") String version);



    //提交预约信息
    @FormUrlEncoded
    @POST("field/commitOrder")
    Observable<HttpRespose<String>> commitOrder1(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    //查询薯田植保记录信息
    @POST("field/listOrder")
    Observable<HttpRespose<String>> listOrder(@Query("data") String data, @Query("deviceToken") String deviceToken, @Query("version") String version);


    //查询薯田植保记录信息
    @GET("field/detailOrder")
    Observable<HttpRespose<String>> detailOrder(@Query("data") String data, @Query("deviceToken") String deviceToken, @Query("version") String version);


//    //提交预约信息
//    @FormUrlEncoded
//    @POST("field/updateOrder")
//    Observable<HttpRespose<String>> updateOrder (@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);



    // 服务站人员评分
    @Multipart
    @POST("field/updateOrder")
    Observable<HttpRespose<String>> updateOrder(@PartMap Map<String, RequestBody> params);


    @FormUrlEncoded
    @POST("field/cancelOrder")
    Observable<HttpRespose<String>> cancelOrder1(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);


    @FormUrlEncoded
    @POST("field/confirmGet")
    Observable<HttpRespose<String>> confirmGet(@Field("data") String data, @Field("deviceToken") String deviceToken, @Field("version") String version);
}
