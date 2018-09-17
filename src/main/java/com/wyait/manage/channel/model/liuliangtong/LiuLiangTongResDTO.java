package com.wyait.manage.channel.model.liuliangtong;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/9/11
 * @description
 */
@Data
public class LiuLiangTongResDTO implements Serializable {

    /**
     * 响应时间
     */
    private String resptime;
    /**
     * 响应状态（非0时，没有msgid）
     */
    private String respstatus;

    /**
     * 服务器给出提交msgid（无论发送多少个号码，一个请求仅对应一个msgid）
     */
    private String msgid;

}
