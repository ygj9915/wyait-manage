package com.wyait.manage.channel.model.gongan;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */
@Data
public class AnHuiGongAnTokenReqDTO implements Serializable {
    private String username;

    private String userkey;

    private String apitoken;

//    private String result;

//    private String time;
}
