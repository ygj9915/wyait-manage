/* https://github.com/orange1438 */
package com.wyait.manage.dao;

import com.wyait.manage.model.ChannelInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * channel_info(渠道信息) 数据库操作
 * @author yeguojian
 * date:2018/09/04 20:37
 */
@Mapper
public interface ChannelInfoMapper {
    /** 
     * 根据ID删除
     * @param id 主键ID
     */
    int deleteByPrimaryKey(Integer id);

    /** 
     * 添加对象所有字段
     * @param record 插入字段对象(必须含ID）
     */
    int insert(ChannelInfoVO record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     */
    int insertSelective(ChannelInfoVO record);

    /** 
     * 根据ID查询
     * @param id 主键ID
     */
    ChannelInfoVO selectByPrimaryKey(Integer id);

    /** 
     * 根据ID修改对应字段
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKeySelective(ChannelInfoVO record);

    /** 
     * 根据ID修改所有字段(必须含ID）
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKey(ChannelInfoVO record);

    /**
     * 根据对象查询
     * @param record 查询字段对象
     */
    List<ChannelInfoVO> selectByParam(ChannelInfoVO record);
}