package com.wyait.manage.channel.model.ronglin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/9/11
 * @description
 */
@Data
public class RongLianResDTO implements Serializable{
    /**
     * 请求状态码
     */
    private String statusCode;

    /**
     * 发送详情
     */
    private RongLianDetailDTO templateSMS;
}
