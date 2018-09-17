package com.wyait.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */
@AllArgsConstructor
@Getter
public enum ChannelResEnum {

    TOKEN_INS_SUC("201", "token插入成功"),
    TOKEN_UPD_SUC("202", "token更新成功"),
    ACCESS_TOKEN_INS_SUC("203", "accessToken插入成功"),
    ACCESS_TOKEN_UPD_SUC("204", "accessToken更新成功"),;

    private String code;

    private String desc;


}
