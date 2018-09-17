package com.wyait.manage.entity;

import java.io.Serializable;

/**
 * @author yeguojian
 * @date on 2018/6/17
 * @description
 */

public class NoteUserDTO implements Serializable{

    private Integer page;

    private Integer limit;

    private Integer userid;

    public String getSmsTitle() {
        return smsTitle;
    }

    public void setSmsTitle(String smsTitle) {
        this.smsTitle = smsTitle;
    }

    private String smsTitle;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}
