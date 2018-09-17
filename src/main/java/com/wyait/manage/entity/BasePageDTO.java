package com.wyait.manage.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/9/4
 * @description
 */
@Data
public class BasePageDTO implements Serializable {
    /**
     * 页码
     */
    private Integer page;

    /**
     * 当前页大小
     */
    private Integer limit;
}
