package com.wyait.manage.channel.model.qianxin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/8/1
 * @description
 */
@Data
public class QianXinNoteReqDTO implements Serializable {
    /**
     * 企业id
     */
    private String userid;
    private String timestamp;
    private String sign;
    private String mobile;
    private String content;
    private String sendTime;
    private String action;
    private String extno;
}
