package com.wyait.manage.channel.model.bigdata;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yeguojian
 * @date on 2018/7/31
 * @description
 */
@Data
public class NoteSendReqDTO implements Serializable {
    private String token;
    private String btype;
    private List<NoteSendDetailReqDTO> mobileContentList;
}
