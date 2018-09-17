package com.wyait.manage.web.channel;

import com.wyait.manage.entity.ChannelGatherDTO;
import com.wyait.manage.model.ChannelInfoVO;
import com.wyait.manage.pojo.User;
import com.wyait.manage.service.ChannelService;
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
@RequestMapping("/channel")
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    private static final Logger log = LoggerFactory
            .getLogger(ChannelController.class);

    @RequestMapping("/channelList")
    public String toUserList() {
        return "/channel/channelList";
    }

    /**
     * 分页查询渠道列表
     * @return ok/fail
     */
    @RequestMapping(value = "/getChannel", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value="channelPage")
    public PageDataResult getNotes(ChannelGatherDTO reqDto) {
        log.debug("分页查询渠道列表！搜索条件：userSearch：" + reqDto);
        PageDataResult pdr = new PageDataResult();
        try {
            if (null == reqDto.getPage()) {
                reqDto.setPage(1);
            }
            if (null == reqDto.getLimit()) {
                reqDto.setLimit(10);
            }
            // 获取渠道列表
            pdr = channelService.queryNoteList(reqDto);
            log.debug("渠道列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("渠道列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping(value = "/getChannelInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getChannelInfo(@RequestParam("id") Integer id){
        log.debug("查询渠道数据！id:" + id);
        Map<String, Object> map = new HashMap<>();
        try {
            if (null == id) {
                log.debug("查询渠道数据==请求参数有误，请您稍后再试");
                map.put("msg", "请求参数有误，请您稍后再试");
                return map;
            }
            // 查询用户
            ChannelInfoVO channelInfoVO = channelService.getChannelById(id);
            log.debug("查询渠道数据！urvo=" + channelInfoVO);
            if (null != channelInfoVO) {
                map.put("channel", channelInfoVO);
                map.put("msg", "ok");
            } else {
                map.put("msg", "查询渠道数据有误，请您稍后再试");
            }
            log.debug("查询渠道数据成功！map=" + map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "查询渠道数据错误，请您稍后再试");
            log.error("查询渠道数据异常！", e);
        }
        return map;
    }

    @RequestMapping(value = "/delChannel",method = RequestMethod.POST)
    @ResponseBody
    public String delNote(@RequestParam("id") Integer id){
        log.debug("删除渠道id:" + id);
        String msg = "";
        try {
            if (null == id ) {
                log.debug("删除渠道，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                log.debug("删除渠道，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除用户
            msg = channelService.delChannel(id);
            log.info("删除渠道:" + msg + "。noteId=" + id + "，操作用户id:"
                    + existUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除渠道异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }

    @RequestMapping(value = "/addChannel",method = RequestMethod.POST)
    @ResponseBody
    public String addChannel(ChannelInfoVO channelInfoVO){
//        log.debug("删除渠道id:" + id);
        String msg = "";
        try {
            if (null == channelInfoVO ) {
                log.debug("删除渠道，结果=请求参数有误，请您稍后再试");
                return "请求参数有误，请您稍后再试";
            }
            User existUser = (User) SecurityUtils.getSubject().getPrincipal();
            if (null == existUser) {
                log.debug("删除渠道，结果=您未登录或登录超时，请您登录后再试");
                return "您未登录或登录超时，请您登录后再试";
            }
            // 删除用户
//            msg = channelService.delChannel(id);
            log.info("删除渠道:" + msg + "。noteId="  + "，操作用户id:"
                    + existUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除渠道异常！", e);
            msg = "操作异常，请您稍后再试";
        }
        return msg;
    }
}
