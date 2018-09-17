package com.wyait.manage.web.recom;

import com.wyait.manage.entity.NoteUserDTO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.service.NoteService;
import com.wyait.manage.utils.PageDataResult;
import com.wyait.manage.web.sms.NoteController;
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

/**
 * @author yeguojian
 * @date on 2018/6/25
 * @description
 */
@Controller
@RequestMapping("/recommend")
public class RecommendController {

    @Autowired
    private NoteService noteService;

    private static final Logger logger = LoggerFactory
            .getLogger(RecommendController.class);

    @RequestMapping("/recomList")
    public String recomList(){
        return "/recommend/recomList";
    }

    /**
     * 分页查询短信列表
     * @return ok/fail
     */
    @RequestMapping(value = "/getNotes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value="recommendShow")
    public PageDataResult getNotes(@RequestParam("page") Integer page,
                                   @RequestParam("limit") Integer limit, NoteUserDTO noteUser) {
        logger.debug("分页查询短信列表！搜索条件：userSearch：" + noteUser + ",page:" + page
                + ",每页记录数量limit:" + limit);
        User user =(User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
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
}
