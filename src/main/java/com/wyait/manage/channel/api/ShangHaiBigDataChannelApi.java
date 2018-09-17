package com.wyait.manage.channel.api;

import com.wyait.manage.channel.model.bigdata.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author yeguojian
 * @date on 2018/7/5
 * @description
 */
public interface ShangHaiBigDataChannelApi {
    @POST("/bigdata/adsm/openapi/v1/login")
    Call<Result<TokenResDTO>> getToken(@Body TokenReqDTO reqDTO);

    @POST("/bigdata/adsm/openapi/v1/send")
    Call<Result<NoteSendResDTO>> senNote(@Body NoteSendReqDTO reqDTO);
}
