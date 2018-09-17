package com.wyait.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yeguojian
 * @date on 2018/7/11
 * @description
 */
@AllArgsConstructor
@Getter
public enum PhoneTypeEnum {

    LT("LT","联通"),
    YD("YD","移动"),
    ;

    private String type;
    private String desc;
}
