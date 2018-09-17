package com.wyait.manage.web.sms;

import com.wyait.manage.entity.NoteUserDTO;
import com.wyait.manage.pojo.NoteUser;
import com.wyait.manage.pojo.User;
import com.wyait.manage.service.NoteService;
import com.wyait.manage.utils.PageDataResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yeguojian
 * @date on 2018/6/17
 * @description
 */
@Controller
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    private static final Logger logger = LoggerFactory
            .getLogger(NoteController.class);

    @RequestMapping("/noteList")
    public String toUserList() {
        return "/note/noteList";
    }

    /**
     * 分页查询短信列表
     * @return ok/fail
     */
    @RequestMapping(value = "/getNotes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value="notePage")
    public PageDataResult getNotes(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit, NoteUserDTO noteUser) {
        logger.debug("分页查询短信列表！搜索条件：userSearch：" + noteUser + ",page:" + page
                + ",每页记录数量limit:" + limit);
        User user =(User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        System.err.println(user);
        noteUser.setUserid(user.getId());
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == page) {
                page = 1;
            }
            if (null == limit) {
                limit = 10;
            }
            // 获取短信列表
            pdr = noteService.queryNoteList(noteUser, page, limit);
            logger.debug("短信列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("短信列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping(value = "/getNoteInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getNoteInfo(@RequestParam("id") Integer id){
        logger.debug("查询短信数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                logger.debug("查询短信数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询用户
            NoteUser noteUser = noteService.getNoteInfoById(id);
            logger.debug("查询短信数据！urvo=" + noteUser);
            if (null != noteUser) {
                map.put("note", noteUser);
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询短信数据有误，请您稍后再试");
            }
            logger.debug("查询用户数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询短信数据错误，请您稍后再试");
            logger.error("查询短信数据异常！", e);
        }
        return map;
    }

    @RequestMapping(value = "/delNote",method = RequestMethod.POST)
    @ResponseBody
    public String delNote(@RequestParam("id") Integer id){
        logger.debug("删除短信！id:" + id);
        String msg = "";
        try {
            if (null == id ) {
                logger.debug("删除短信，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                logger.debug("删除短信，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除用户
            msg = noteService.delNote(id);
            logger.info("删除短信:" + msg + "。noteId=" + id + "，操作用户id:"
                    + existUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除短信异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }
}
