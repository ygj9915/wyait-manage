package com.wyait.manage.channel.model.st;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/7/11
 * @description
 */
@Data
public class SaiTianNoteReqDTO implements Serializable{

    /**
     * 用户的AccessKey
     */
    private String accesskey;

    /**
     * 用户的AccessSecre
     */
    private String secret;

    /**
     * 签名
     */
    private String sign;

    /**
     * 模板id
     */
    private String templateId;

    /**
     * 收短信的手机号码，多个号码以半角逗号,隔开
     */
    private String mobile;

    /**
     * 发送的短信内容是模板变量内容
     */
    private String content;

    /**
     *
     */
    private String data;

    /**
     * 发送时间
     */
    private String scheduleSendTime;
}
