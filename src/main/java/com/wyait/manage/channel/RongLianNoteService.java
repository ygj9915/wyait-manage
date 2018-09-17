package com.wyait.manage.channel;

import com.alibaba.fastjson.JSONObject;
import com.wyait.manage.channel.model.ronglin.RLConfig;
import com.wyait.manage.channel.model.ronglin.RongLianResDTO;
import com.wyait.manage.enums.ChannelCodeEnums;
import com.wyait.manage.enums.SendStatusEnum;
import com.wyait.manage.manager.NotesResultManager;
import com.wyait.manage.utils.DateUtils;
import com.wyait.manage.utils.HttpResult;
import com.wyait.manage.utils.HttpService;
import com.wyait.manage.utils.MD5Utils;
import com.wyait.manage.utils.base64.Base64;
import com.wyait.manage.utils.http.HttpUtils;
import com.wyait.manage.utils.http.model.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yeguojian
 * @date on 2018/9/11
 * @description
 */

@Service
@Slf4j
public class RongLianNoteService extends BaseNoteApi {

    @Autowired
    private NotesResultManager notesResultManager;

    @Override
    public String getChannelCode() {
        return ChannelCodeEnums.RL.getCode();
    }

    @Override
    public void sendNotes(String signature, String content, List<String> phones, String phoneType) {
        List<String> sendPhones = splitList(phones,packetSize());

        for(String mobile : sendPhones){
            if(!processChannel(mobile,signature,content,phoneType)){
                return;
            }
        }
    }

    private boolean processChannel(String phone,String signature,String content,String phoneType){
        RLConfig config = new RLConfig();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appId", config.getAppId());
        jsonObject.put("to", phone);
        jsonObject.put("templateId", content);
        String str[] = new String[]{content};
        jsonObject.put("datas",str);

        String time = DateUtils.getDateToString(new Date(),DateUtils.DATE_TYPE_4);
        String sigParameter = config.getAccountSid() + config.getToken()+ time;
        String sig = MD5Utils.md5(sigParameter ,"utf-8");
        String url = config.getUrl() + "/" + config.getSoftVersion() + "/" + "Accounts" + "/" + config.getAccountSid()
                + "/" + config.getFunc() + "/" + config.getFuncdes() + "?" + "sig=" + sig;
        //拼装请求头
        String authorization = config.getAccountSid() + ":" + time;
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("Authorization", Base64.encode(authorization));
        log.info("容联发送信息：{},地址：{}" , jsonObject.toString() , url);
        long startTime = System.currentTimeMillis();
        ClientResponse clientResponse;
        try{
            clientResponse = HttpUtils.httpPostJson(url,jsonObject.toJSONString(),headers);
            log.info("容联渠道耗时：{}",System.currentTimeMillis() - startTime);
        }catch (Exception e){
            //请求异常
            log.error("容联 短信下发发送异常：", e);
            return false;
        }
        String result = clientResponse.getBody();
        if (StringUtils.isBlank(result)){
            log.error("容联 短信下发发送异常：", result);
            return false;
        }
        RongLianResDTO resDTO = JSONObject.parseObject(result,RongLianResDTO.class);
        if("000000".equals(resDTO.getStatusCode())){
            notesResultManager.insetBatch(phoneType,signature,phone,1,getChannelCode(), SendStatusEnum.SEND_SUCCESS,resDTO.getTemplateSMS().getSmsMessageSid());
            return true;
        }
        return false;
    }


    @Override
    public int packetSize() {
        return 200;
    }
}
