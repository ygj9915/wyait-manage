package com.wyait.manage.channel.model.gongan;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */
@Data
public class AnHuiGongAnAccessTokenReqDTO implements Serializable {
    private String code;

    private String contentid;

    private String phone;
}
