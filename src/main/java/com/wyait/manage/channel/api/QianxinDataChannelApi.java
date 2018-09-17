package com.wyait.manage.channel.api;

import com.wyait.manage.channel.model.qianxin.QianXinNoteReqDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author yeguojian
 * @date on 2018/7/5
 * @description
 */
public interface QianxinDataChannelApi {
    @POST("/v2sms.aspx")
    Call<Object> sendQinXinNote(@Body QianXinNoteReqDTO reqDTO);
}
