package com.wyait.manage.channel.model.oneself;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/8/23
 * @description
 */
@Data
public class OneselfResDTO implements Serializable {
    private String resultCode;

    private String resultMsg;

    private String result;

    private String time;
}
