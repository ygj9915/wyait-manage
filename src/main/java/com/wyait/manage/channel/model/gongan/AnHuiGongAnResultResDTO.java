package com.wyait.manage.channel.model.gongan;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/7/9
 * @description
 */
@Data
public class AnHuiGongAnResultResDTO implements Serializable{

    private Boolean result;

    private String category;

    private String message;

    private String allcount;

    private String sendsuccess;

    private String sendfail;

    private String failphone;
}
