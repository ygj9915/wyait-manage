package com.wyait.manage.channel;

import com.alibaba.fastjson.JSONObject;
import com.wyait.manage.channel.api.ShangHaiBigDataChannelApi;
import com.wyait.manage.channel.model.st.SaiTianNoteReqDTO;
import com.wyait.manage.channel.model.st.SaiTianNoteResDTO;
import com.wyait.manage.enums.ChannelCodeEnums;
import com.wyait.manage.enums.PhoneTypeEnum;
import com.wyait.manage.enums.SendStatusEnum;
import com.wyait.manage.manager.NotesResultManager;
import com.wyait.manage.utils.HttpResult;
import com.wyait.manage.utils.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yeguojian
 * @date on 2018/7/11
 * @description
 */
@Service
@Slf4j
public class SaiTianNoteServices extends BaseNoteApi{

    private static final String YD_ACCESSKEY = "s4hc1XV0ejZ0I4Ws";

    private static final String YD_SECRET = "DbBwLEFMTt8HkRy2I8ODPaoUZjHUdq0C";

    private static final String LT_ACCESSKEY = "8boJw7Hh1MHw6IwV";

    private static final String LT_SECRET = "wGkvK8wWpNQGDq0IOteynVFpjztvQUBY";

    @Autowired
    private HttpService httpService;

    @Autowired
    private ShangHaiBigDataChannelApi saiTianChannelApi;

    @Autowired
    private NotesResultManager notesResultManager;

    @Override
    public String getChannelCode() {
        return ChannelCodeEnums.ST.getCode();
    }

    @Override
    public void sendNotes(String signature ,String content, List<String> phones, String phoneType) {
        List<String> sendList = splitList(phones,packetSize());
        for(String phone: sendList){
            reqestChannel(signature,phone,content,phoneType);
        }
    }

    @Override
    public int packetSize() {
        return 50000;
    }

    public void reqestChannel(String signature,String phone,String content, String phoneType){
        SendStatusEnum statusEnum = SendStatusEnum.SEND_FAIL;
        SaiTianNoteReqDTO reqDTO = buildReq(phone,content,phoneType);
        try {
            //新获取apitoken
            Map<String, Object> map = buildReqMap(phone,content,phoneType);
            log.info("发送请求短信渠道对象：{}", map);
            HttpResult result = httpService.doPost("http://api.1cloudsp.com/api/v2/send", map);
            log.info("发送请求短信渠道结果：{}", result.getBody());
            if (result.getCode() == 200) {
                SaiTianNoteResDTO resDTO = JSONObject.parseObject(result.getBody().toString(), SaiTianNoteResDTO.class);
                log.info("赛天渠道返回结果：{}",resDTO);
                if("0".equals(resDTO.getCode())){
                    statusEnum = SendStatusEnum.SEND_SUCCESS;
                    return ;
                }
            }
        }catch (Exception e){
            log.error("调用赛天渠道异常：",e);
        }finally {
            notesResultManager.insetSplitBatch(signature,phone,1,getChannelCode(),statusEnum);
        }
    }

    private SaiTianNoteReqDTO buildReq(String phone, String content, String phoneType){
        SaiTianNoteReqDTO reqDTO = new SaiTianNoteReqDTO();
        if(PhoneTypeEnum.LT.getType().equals(phoneType)){
            reqDTO.setAccesskey(LT_ACCESSKEY);
            reqDTO.setSecret(LT_SECRET);
        }else{
            reqDTO.setAccesskey(YD_ACCESSKEY);
            reqDTO.setSecret(YD_SECRET);
        }
        reqDTO.setSign("【万达普惠】");
        reqDTO.setContent(content);
        reqDTO.setMobile(phone);
        return reqDTO;
    }

    private Map<String,Object> buildReqMap(String phone, String content, String phoneType){
        Map<String,Object> map = new HashMap<>();
        if(PhoneTypeEnum.YD.getType().equals(phoneType)){
            map.put("accesskey",YD_ACCESSKEY);
            map.put("secret",YD_SECRET);
        }else{
            map.put("accesskey",LT_ACCESSKEY);
            map.put("secret",LT_SECRET);
        }
        map.put("sign","【万达普惠】");
        map.put("content",content);
        map.put("mobile",phone);
        return map;
    }
}
