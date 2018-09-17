package com.wyait.manage.channel.model.ronglin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/9/11
 * @description
 */
@Data
public class RongLianDetailDTO implements Serializable{
    /**
     * 短信的创建时间，格式：yyyyMMddHHmmss
     */
    private String dateCreated;

    /**
     * 短信唯一标识符
     */
    private String smsMessageSid;
}
