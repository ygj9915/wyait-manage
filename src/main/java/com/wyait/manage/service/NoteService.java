package com.wyait.manage.service;

import com.wyait.manage.entity.NoteUserDTO;
import com.wyait.manage.pojo.NoteUser;
import com.wyait.manage.utils.PageDataResult;

/**
 * @author yeguojian
 * @date on 2018/6/17
 * @description
 */
public interface NoteService {


    PageDataResult queryNoteList(NoteUserDTO noteUserDTO, int page, int limit);

    NoteUser getNoteInfoById(int id);

    String delNote(Integer id);
}
