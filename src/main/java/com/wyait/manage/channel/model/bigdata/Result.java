package com.wyait.manage.channel.model.bigdata;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/7/31
 * @description
 */
@Data
public class Result<T> implements Serializable {
    private String resCode;
    private String resDesc;
    private T resData;
}
