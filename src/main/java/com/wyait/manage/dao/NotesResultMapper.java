/* https://github.com/orange1438 */
package com.wyait.manage.dao;


import com.wyait.manage.entity.NotesResultDTO;

import java.util.List;

/**
 * notes_result(短信结果表
) 数据库操作
 * @author yeguojian
 * date:2018/07/09 14:39
 */
public interface NotesResultMapper {
    /** 
     * 根据ID删除
     * @param id 主键ID
     */
    int deleteByPrimaryKey(Long id);

    /** 
     * 添加对象所有字段
     * @param record 插入字段对象(必须含ID）
     */
    int insert(NotesResultDTO record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     */
    int insertSelective(NotesResultDTO record);

    /** 
     * 根据ID查询
     * @param id 主键ID
     */
    NotesResultDTO selectByPrimaryKey(Long id);

    /** 
     * 根据ID修改对应字段
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKeySelective(NotesResultDTO record);

    /** 
     * 根据ID修改所有字段(必须含ID）
     * @param record 修改字段对象(必须含ID）
     */
    int updateByPrimaryKey(NotesResultDTO record);

    /**
     * 批量插入数据
     * @param list
     * @return
     */
    int insertBatchSelective(List<NotesResultDTO> list);
}