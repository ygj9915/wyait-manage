package com.wyait.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wyait.manage.dao.NoteUserMapper;
import com.wyait.manage.entity.NoteUserDTO;
import com.wyait.manage.pojo.NoteUser;
import com.wyait.manage.service.NoteService;
import com.wyait.manage.utils.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yeguojian
 * @date on 2018/6/17
 * @description
 */
@Service
public class NoteServiceImp implements NoteService {

    @Autowired
    private NoteUserMapper noteUserMapper;

    @Override
    public PageDataResult queryNoteList(NoteUserDTO noteUserDTO, int page, int limit) {
        NoteUser noteUser = new NoteUser();
        noteUser.setSmsTitle(noteUserDTO.getSmsTitle());
        noteUser.setUserId(noteUserDTO.getUserid());
        PageDataResult pdr = new PageDataResult();
        PageHelper.startPage(page, limit);
        List<NoteUser> urList = noteUserMapper.getNotes(noteUser);
        // 获取分页查询后的数据
        PageInfo<NoteUser> pageInfo = new PageInfo<>(urList);
        // 设置获取到的总记录数total：
        pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
        pdr.setList(urList);
        return pdr;
    }

    @Override
    public NoteUser getNoteInfoById(int id) {
        return noteUserMapper.getNoteById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=30000,rollbackFor={RuntimeException.class, Exception.class})
    public String delNote(Integer id) {
        NoteUser noteUser = noteUserMapper.getNoteById(id);
        if(noteUser == null){
            return "删除失败";
        }
        int i = noteUserMapper.delNote(id);
        if(i == 0){
            return "删除失败";
        }
        return "ok";
    }
}
