package com.wyait.manage.channel;

import com.alibaba.fastjson.JSONObject;
import com.wyait.manage.channel.model.liuliangtong.LiuLiangTongResDTO;
import com.wyait.manage.enums.ChannelCodeEnums;
import com.wyait.manage.enums.SendStatusEnum;
import com.wyait.manage.manager.NotesResultManager;
import com.wyait.manage.utils.http.HttpUtils;
import com.wyait.manage.utils.http.model.ClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yeguojian
 * @date on 2018/9/11
 * @description
 */
@Service
@Slf4j
public class LiuliangTongNoteService extends BaseNoteApi {

    @Autowired
    private NotesResultManager notesResultManager;

    @Override
    public String getChannelCode() {
        return ChannelCodeEnums.LLT.getCode();
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

    private boolean processChannel(String phone,String signature,String content,String phoneType) {
        String url = "http://118.31.188.220/msg/HttpBatchSendSM?account=llt-88964778&pswd=PW93747a9f&mobile="+phone+"&msg="+content+"&needstatus=true";

        long startTime = System.currentTimeMillis();
        ClientResponse clientResponse;
        try{
            clientResponse = HttpUtils.httpGet(url,null,null);
            log.info("流量通渠道耗时：{}",System.currentTimeMillis() - startTime);
        }catch (Exception e){
            //请求异常
            log.error("流量通 短信下发发送异常：", e);
            return false;
        }
        String result = clientResponse.getBody();

        String[] resData = result.split(",");
        String[] status = resData[1].split("\n");

        if (StringUtils.isBlank(result)){
            log.error("流量通 短信下发发送异常：", result);
            return false;
        }
        if("0".equals(status[0])){
            notesResultManager.insetBatch(phoneType,signature,phone,1,getChannelCode(), SendStatusEnum.SEND_SUCCESS,status[1]);
            return true;
        }
        return false;
    }

        @Override
    public int packetSize() {
        return 50000;
    }
}
