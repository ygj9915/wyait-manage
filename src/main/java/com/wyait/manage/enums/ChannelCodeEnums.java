package com.wyait.manage.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 渠道功能枚举
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */

@AllArgsConstructor
@Getter
public enum ChannelCodeEnums  {

    AHGA("AHGA","安徽公安渠道"),
    ST("ST","聚天短信"),
    SHBD("SHBD","上海联通大数据平台"),
    QX("QX","千信渠道"),
    ONESELF("ONESELF","自有渠道"),
    RL("RL","容联渠道"),
    LLT("LLT","流量通渠道"),
    ;

    private String code;

    private String desc;
}
