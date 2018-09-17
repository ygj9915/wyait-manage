package com.wyait.manage.channel.model.bigdata;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/7/31
 * @description
 */
@Data
public class NoteSendDetailResDTO implements Serializable {
    private String mid;
    private String mobile;
    private String resultCode;
}
