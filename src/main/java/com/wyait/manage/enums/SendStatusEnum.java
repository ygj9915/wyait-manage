package com.wyait.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yeguojian
 * @date on 2018/7/9
 * @description
 */
@AllArgsConstructor
@Getter
public enum SendStatusEnum {

    SEND_SUCCESS("SEND_SUCCESS","发送成功"),
    SEND_FAIL("SEND_FAIL","发送失败")
    ;

    private String status;

    private String desc;

}
