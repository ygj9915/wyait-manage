package com.wyait.manage.web.channel.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/9/5
 * @description
 */
@Data
public class ChannelReqDO implements Serializable{

    /** 渠道编号 */
    private String channelCode;

    /** 渠道注释 */
    private String channelDesc;

    /** 渠道金额 */
    private String channelPrice;

//    private String
}
