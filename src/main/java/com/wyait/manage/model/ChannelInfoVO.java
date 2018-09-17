/* https://github.com/orange1438 */
package com.wyait.manage.model;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/** 
 * 渠道信息 channel_info
 * @author yeguojian
 * date:2018/09/04 20:37
 */
@Data
public class ChannelInfoVO implements Serializable {
    /** 串行版本ID*/
    private static final long serialVersionUID = 3017672406446325367L;

    /** id主键 */
    private Integer id;

    /** 渠道编号 */
    private String channelCode;

    /** 渠道注释 */
    private String channelDesc;

    /** 渠道金额 */
    private String channelPrice;

    /** 创建时间 */
    private Date createdAt;

    /** 创建人 */
    private String createdBy;

    /** 更新时间 */
    private Date updatedAt;

    /** 更新人 */
    private String updatedBy;
}