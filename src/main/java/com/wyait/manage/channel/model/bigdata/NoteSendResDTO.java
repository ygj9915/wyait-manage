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
public class NoteSendResDTO implements Serializable {
    private String successCount;
    private List<NoteSendDetailResDTO> respList;
}
