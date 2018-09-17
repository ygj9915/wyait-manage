package com.wyait.manage.service;

import com.wyait.manage.entity.ChannelGatherDTO;
import com.wyait.manage.model.ChannelInfoVO;
import com.wyait.manage.utils.PageDataResult;

/**
 * 渠道配置
 * @author yeguojian
 * @date on 2018/9/4
 * @description
 */
public interface ChannelService {
    /**
     * 查询渠道列表信息
     * @param channelGatherDTO 页面请求信息
     * @return
     */
    PageDataResult queryNoteList(ChannelGatherDTO channelGatherDTO);

    /**
     * 商户渠道信息
     * @param id 渠道id
     * @return
     */
    String delChannel(Integer id);

    /**
     * 通过id查询渠道信息
     * @param id 渠道id
     * @return 渠道信息
     */
    ChannelInfoVO getChannelById(Integer id);
}
