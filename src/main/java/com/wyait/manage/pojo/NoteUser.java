package com.wyait.manage.pojo;

import java.io.Serializable;
import java.util.Date;

/** 
 * @author yeguojian
 * date:2018/06/17 01:03
 */

public class NoteUser implements Serializable {
    /** 串行版本ID*/
    private static final long serialVersionUID = -5748612010413863826L;

    /** id主键 */
    private Integer id;

    /** 模板标题 */
    private String smsTitle;

    /** 用户主键id */
    private Integer userId;

    /** 短信内容 */
    private String smsTemplate;

    /** 添加时间 */
    private Date insertTime;

    /** 更新时间 */
    private Date updateTime;

    public String getSmsTitle() {
        return smsTitle;
    }

    public void setSmsTitle(String smsTitle) {
        this.smsTitle = smsTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}