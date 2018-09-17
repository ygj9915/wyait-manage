package com.wyait.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.ChannelInfoMapper;
import com.wyait.manage.entity.ChannelGatherDTO;
import com.wyait.manage.model.ChannelInfoVO;
import com.wyait.manage.service.ChannelService;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yeguojian
 * @date on 2018/9/4
 * @description
 */
@Component
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelInfoMapper channelInfoMapper;

    @Override
    public PageDataResult queryNoteList(ChannelGatherDTO channelGatherDTO) {
        ChannelInfoVO channelInfoVO = new ChannelInfoVO();
        channelInfoVO.setChannelCode(channelGatherDTO.getChannelCode());
        channelInfoVO.setChannelDesc(channelGatherDTO.getChannelDesc());
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(channelGatherDTO.getPage(), channelGatherDTO.getLimit());
        List<ChannelInfoVO> urList = channelInfoMapper.selectByParam(channelInfoVO);
        // 获取分页查询后的数据
        PageInfo<ChannelInfoVO> pageInfo = new PageInfo<>(urList);
        // 设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(urList);
        return pdr;
    }

    @Override
    public String delChannel(Integer id) {
        ChannelInfoVO noteUser = channelInfoMapper.selectByPrimaryKey(id);
        if(noteUser == null){
            return "删除失败";
        }
        int i = channelInfoMapper.deleteByPrimaryKey(id);
        if(i == 0){
            return "删除失败";
        }
        return "ok";
    }

    @Override
    public ChannelInfoVO getChannelById(Integer id) {
        return channelInfoMapper.selectByPrimaryKey(id);
    }
}
