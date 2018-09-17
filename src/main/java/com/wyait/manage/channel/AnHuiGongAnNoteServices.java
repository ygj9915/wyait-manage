package com.wyait.manage.channel;

import com.alibaba.fastjson.JSONObject;
import com.wyait.manage.channel.model.gongan.AnHuiGongAnResultResDTO;
import com.wyait.manage.channel.model.gongan.AnHuiGongAnTokenResDTO;
import com.wyait.manage.channel.model.oneself.OneselfResDTO;
import com.wyait.manage.enums.ChannelCodeEnums;
import com.wyait.manage.enums.ChannelResEnum;
import com.wyait.manage.enums.PhoneTypeEnum;
import com.wyait.manage.enums.SendStatusEnum;
import com.wyait.manage.manager.NotesResultManager;
import com.wyait.manage.utils.HttpResult;
import com.wyait.manage.utils.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */
@Slf4j
@Service
public class AnHuiGongAnNoteServices extends BaseNoteApi {
    @Autowired
    private HttpService httpService;

    @Autowired
    private NotesResultManager notesResultManager;

    @Override
    public String getChannelCode() {
        return ChannelCodeEnums.AHGA.getCode();
    }

    @Override
    public void sendNotes(String signature , String content, List<String> phones,String phoneType) {
        List<String> sendPhones = splitList(phones,packetSize());

        String token = getTokenDTO();
        if (token == null) {
            return;
        }
        String accessToken = getAccessToken(token);
        if (accessToken == null) {
            return;
        }
        for (String phone : sendPhones) {
            if(PhoneTypeEnum.YD.getType().equals(phoneType)){
                sendYDMessage(phoneType,signature,accessToken,phone,content);
            }else{
                sendMessage(phoneType,signature,accessToken, phone, content);
            }
        }
    }

    @Override
    public int packetSize() {
        return 1000;
    }

    /**
     * 获取token
     *
     * @return
     */
    private String getTokenDTO() {
        try {
            //新获取apitoken
            Map<String, Object> map = new HashMap<>();
            map.put("username", "jtkj");
//            map.put("userkey", "5W8cb#0jha");
            map.put("userkey", "kiopknci");
            log.info("请求安徽公安渠道对象：{}", map);
//            HttpResult result = httpService.doPost("http://api3.data2share.com:13580/sms/TokenResDTO/getApiToken", map);
            HttpResult result = httpService.doPost("http://api3.data2share.com:13580/q/Token/getApiToken", map);
            if (result.getCode() == 200) {
                log.info(result.getBody().toString());
                AnHuiGongAnTokenResDTO resDTO = JSONObject.parseObject(result.getBody().toString(), AnHuiGongAnTokenResDTO.class);
                if (ChannelResEnum.TOKEN_INS_SUC.getCode().equals(resDTO.getResultCode())
                        || ChannelResEnum.TOKEN_UPD_SUC.getCode().equals(resDTO.getResultCode())) {
                    return resDTO.getResult();
                }
            }
            return null;
        } catch (Exception e) {
            log.error("调用渠道异常：{}", e);
            return null;
        }
    }

    /**
     * 获取accessToken
     *
     * @param apiToken
     * @return
     */
    private String getAccessToken(String apiToken) {
        try {
            //新获取apitoken
            Map<String, Object> map = new HashMap<>();
            map.put("username", "jtkj");
            map.put("apitoken", apiToken);
            log.info("请求安徽公安渠道对象：{}", map);
            HttpResult result = httpService.doPost("http://api3.data2share.com:13580/q/Token/getAccessToken", map);
            if (result.getCode() == 200) {
                AnHuiGongAnTokenResDTO resDTO = JSONObject.parseObject(result.getBody().toString(), AnHuiGongAnTokenResDTO.class);
                if (ChannelResEnum.ACCESS_TOKEN_INS_SUC.getCode().equals(resDTO.getResultCode())
                        || ChannelResEnum.ACCESS_TOKEN_UPD_SUC.getCode().equals(resDTO.getResultCode())) {

                    return resDTO.getResult();
                }
            }
            return null;
        } catch (Exception e) {
            log.error("调用渠道异常：{}", e);
            return null;
        }
    }

    private Boolean sendMessage(String phoneType ,String signature,String accessToken, String phone, String content) {
        SendStatusEnum statusEnum = SendStatusEnum.SEND_FAIL;
        try {
            //新获取apitoken
            Map<String, Object> map = new HashMap<>();
            map.put("ckey", accessToken);
            map.put("contentid", content);
            map.put("phone", phone);
            log.info("发送请求短信渠道对象：{}", map);
            long start = System.currentTimeMillis();
            HttpResult result = httpService.doPost("http://120.52.10.10:30055/SGIP/DXService/SendMessage", map);
            log.info("发送请求短信渠道耗时：{},结果：{}",System.currentTimeMillis()-start, result.getBody());
            if (result.getCode() == 200) {
                AnHuiGongAnResultResDTO resDTO = JSONObject.parseObject(result.getBody().toString(), AnHuiGongAnResultResDTO.class);
                if (resDTO.getResult()) {
                    statusEnum = SendStatusEnum.SEND_SUCCESS;
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("调用渠道异常：{}", e);
            return false;
        }finally {
            notesResultManager.insetBatch(phoneType,signature ,phone,1,getChannelCode(),statusEnum);
        }
    }


    private Boolean sendYDMessage(String phoneType,String signature,String accessToken, String phone, String content) {
        SendStatusEnum statusEnum = SendStatusEnum.SEND_FAIL;
        try {
            //新获取apitoken
            Map<String, Object> map = new HashMap<>();
            map.put("token", accessToken);
            map.put("username", "jtkj");
            map.put("contentid", content);
            map.put("pdid", "801001");
            map.put("phone", phone);
            log.info("发送请求短信渠道对象：{}", map);
            long start = System.currentTimeMillis();
            HttpResult result = httpService.doPost("http://api3.data2share.com:13580/q/A/apiV2", map);
            log.info("发送请求短信渠道耗时：{},结果：{}",System.currentTimeMillis()-start, result.getBody());
            if (result.getCode() == 200) {
                OneselfResDTO resDTO = JSONObject.parseObject(result.getBody().toString(), OneselfResDTO.class);
                if ("1".equals(resDTO.getResultCode())) {
                    statusEnum = SendStatusEnum.SEND_SUCCESS;
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("调用渠道异常：{}", e);
            return false;
        }finally {
            notesResultManager.insetBatch(phoneType,signature,phone,1,getChannelCode(),statusEnum);
        }
    }

}
