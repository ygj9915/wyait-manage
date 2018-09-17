package com.wyait.manage.channel;

import com.alibaba.fastjson.JSONObject;
import com.wyait.manage.channel.api.ShangHaiBigDataChannelApi;
import com.wyait.manage.channel.model.bigdata.*;
import com.wyait.manage.channel.model.st.SaiTianNoteResDTO;
import com.wyait.manage.enums.ChannelCodeEnums;
import com.wyait.manage.enums.SendStatusEnum;
import com.wyait.manage.manager.NotesResultManager;
import com.wyait.manage.utils.HttpResult;
import com.wyait.manage.utils.HttpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;

import java.util.*;

/**
 * @author yeguojian
 * @date on 2018/7/31
 * @description
 */
@Service
@Slf4j
public class ShangHaiBigDataNoteServices extends BaseNoteApi {

    @Autowired
    private HttpService httpService;

    @Autowired
    private ShangHaiBigDataChannelApi shangHaiBigDataChannelApi;

    @Autowired
    private NotesResultManager notesResultManager;

    private static final String url= "https://211.95.17.123:29088/bigdata/adsm/openapi/v1/send";

    @Override
    public String getChannelCode() {
        return ChannelCodeEnums.SHBD.getCode();
    }

    @Override
    public void sendNotes(String signature, String content, List<String> phones, String phoneType) {
        Long start = System.currentTimeMillis();
        List<List<String>> newList = splitListM(phones,packetSize());
        String token = getToken();
        for(List<String> subList:newList){
            if(System.currentTimeMillis()-start>=1000*60*10L){
                token = getToken();
            }
            if(token == null){
                return;
            }
            sendNote(signature,token,subList,content);
        }
    }

    @Override
    public int packetSize() {
        return 1000;
    }

    public boolean sendNote(String signature, String token, List<String> phones, String content) {
        try {
            List<NoteSendDetailReqDTO> list = new ArrayList<>();
            for (String phone : phones) {
                NoteSendDetailReqDTO detailDTO = new NoteSendDetailReqDTO();
                detailDTO.setMobile(phone);
                detailDTO.setContent(content);
                list.add(detailDTO);
            }

            Map<String,Object> map = new HashMap<>();
            map.put("btype","1");
            map.put("mobileContentList",list);
            NoteSendReqDTO noteSendReqDTO = new NoteSendReqDTO();
            noteSendReqDTO.setBtype("1");
            noteSendReqDTO.setMobileContentList(list);
            HttpResult httpResult = httpService.doPostHeader(url,map,token);
            if (httpResult.getCode() == 200) {
                Result<Object> resDTO = JSONObject.parseObject(httpResult.getBody().toString(), Result.class);
                log.info("请求上海大数据渠道对象：{}",resDTO);
                notesResultManager.insetBatch(signature,JSONObject.parseObject(resDTO.getResData().toString(),NoteSendResDTO.class),1,getChannelCode());
                return true;
            }
        }catch (Exception e){
            log.error("调用渠道异常：{}", e);
            return false;
        }
        return false;
    }

    public String getToken() {
        try {
            TokenReqDTO reqDTO = new TokenReqDTO();
            reqDTO.setUsername("wdph01");
            reqDTO.setPassword("ADW#lpmdpmr");
            reqDTO.setSecrect("adsm_wdph");
            log.info("请求上海大数据渠道对象：{}", reqDTO);
            Call<Result<TokenResDTO>> call = shangHaiBigDataChannelApi.getToken(reqDTO);
            Response<Result<TokenResDTO>> response = call.execute();
            if (response.code() == 200) {
                Result<TokenResDTO> result = response.body();
                log.info("请求上海大数据渠道结果", response.body());
                if ("0000".equals(result.getResCode())) {
                    return result.getResData().getToken();
                }
            }
            return null;
        } catch (Exception e) {
            log.error("调用渠道异常：{}", e);
            return null;
        }
    }

    /**
     * 分割list
     *
     * @param list
     * @return
     */
    private static List<List<String>> splitListM(List<String> list, int groupSize) {
        int length = list.size();
        // 计算可以分成多少组
        int num = (length + groupSize - 1) / groupSize;
        List<List<String>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * groupSize;
            // 结束位置
            int toIndex = (i + 1) * groupSize < length ? (i + 1) * groupSize : length;
            newList.add(list.subList(fromIndex, toIndex));
        }
        return newList;
    }

}
