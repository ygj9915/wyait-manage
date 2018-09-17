package com.wyait.manage.dao;


import com.wyait.manage.pojo.NoteUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * note_user 数据库操作
 * @author yeguojian
 * date:2018/06/17 01:03
 */
@Mapper
public interface NoteUserMapper {
    /** 
     * 添加对象所有字段
     * @param record 插入字段对象(必须含ID）
     */
    int insert(NoteUser record);

    /** 
     * 添加对象对应字段
     * @param record 插入字段对象(必须含ID）
     */
    int insertSelective(NoteUser record);

    /**
     * 分页查询note
     * @param record
     * @return
     */
    List<NoteUser> getNotes(@Param("noteUser") NoteUser record);

    NoteUser getNoteById(@Param("id") Integer id);

    int delNote(@Param("id") Integer id);
}