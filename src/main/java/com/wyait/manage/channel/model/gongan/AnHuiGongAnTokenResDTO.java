package com.wyait.manage.channel.model.gongan;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */
@Data
public class AnHuiGongAnTokenResDTO implements Serializable {
    private String resultCode;

    private String resultMsg;

    private String result;

    private String time;
}
