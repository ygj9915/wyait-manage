package com.wyait.manage.channel.model.ronglin;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/9/11
 * @description
 */
@Data
public class RLConfig implements Serializable {
    /**
     * 软件版本
     * 格式：YYYY-MM-DD，当前版本：2013-12-26
     */
    private String softVersion = "2013-12-26";

    /**
     * 主账户Id
     */
    private String accountSid = "8a216da865c27c8d0165cbac30a50590";

    /**
     * 业务功能
     */
    private String func = "SMS";

    /**
     * 业务操作
     */
    private String funcdes = "TemplateSMS";

    /**
     * 应用Id
     */
    private String appId = "8a216da865c27c8d0165cbac31070597";

    /**
     * token
     */
    private String token = "a3dd480724304c8195664ae5917ef630";

    /**
     * 接口地址
     */
    private String url = "https://app.cloopen.com:8883";

    /**
     *  短信模版id
     */
    private String templateId;
}
