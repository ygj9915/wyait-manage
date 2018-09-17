package com.wyait.manage.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/** 
 * 短信结果表 notes_result
 * @author yeguojian
 * date:2018/07/09 14:39
 */
@Data
public class NotesResultDTO implements Serializable {
    /** 串行版本ID*/
    private static final long serialVersionUID = 8355314495240615108L;

    private Long id;

    /** 用户id */
    private Integer userId;

    /** 手机号 */
    private String phone;

    /** 渠道编码 */
    private String channelCode;

    /** 发送状态 */
    private String sendStatus;

    /** 批次号 */
    private String batchCode;

    /** 签名 */
    private String signature;

    /** 运营商 */
    private String phoneType;

    /** 创建时间 */
    private Date createdAt;

    /** 创建人 */
    private String createdBy;

    /** 更新时间 */
    private Date updatedAt;

    /** 更新人 */
    private String updatedBy;
}