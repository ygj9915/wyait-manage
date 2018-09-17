package com.wyait.manage.channel.api;

import com.wyait.manage.channel.model.oneself.OneselfResDTO;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * @author yeguojian
 * @date on 2018/8/23
 * @description
 */
public interface OneselfChannelApi {
    @POST("/q/a/apiV2")
    Call<OneselfResDTO> sendOneNote(@QueryMap Map<String,Object> map);
}
