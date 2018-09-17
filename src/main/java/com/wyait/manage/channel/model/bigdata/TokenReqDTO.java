package com.wyait.manage.channel.model.bigdata;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/7/31
 * @description
 */
@Data
public class TokenReqDTO implements Serializable {
    private String username;
    private String password;
    private String secrect;
}
