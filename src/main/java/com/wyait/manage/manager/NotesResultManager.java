/* https://github.com/orange1438 */
package com.wyait.manage.manager;


import com.wyait.manage.channel.model.bigdata.NoteSendDetailResDTO;
import com.wyait.manage.channel.model.bigdata.NoteSendResDTO;
import com.wyait.manage.dao.NotesResultMapper;
import com.wyait.manage.entity.NotesResultDTO;
import com.wyait.manage.enums.SendStatusEnum;
import com.wyait.manage.utils.Constants;
import com.wyait.manage.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * notes_result(短信结果表) 数据库操作
 * @author yeguojian
 * date:2018/07/09 14:39
 */
@Service
@Slf4j
public class NotesResultManager<T> {

    @Autowired
    private NotesResultMapper notesResultMapper;

    public void insetBatch(String phoneType,String signature,String phones, int userId, String channelCode, SendStatusEnum statusEnum){
       try {

           List<String> strings = Arrays.asList(phones.split(","));
           String batchCode = DateUtils.getDateTime();
           List<NotesResultDTO> dataList = new ArrayList<>();
           for (String phone : strings) {
               NotesResultDTO resultDTO = new NotesResultDTO();
               resultDTO.setBatchCode(batchCode);
               resultDTO.setChannelCode(channelCode);
               resultDTO.setCreatedBy(Constants.PRODCUT_NAME);
               resultDTO.setCreatedAt(new Date());
               resultDTO.setPhone(phone);
               resultDTO.setSendStatus(statusEnum.getStatus());
               resultDTO.setUserId(userId);
               resultDTO.setSignature(signature);
               resultDTO.setPhoneType(phoneType);
               dataList.add(resultDTO);
           }
           notesResultMapper.insertBatchSelective(dataList);
       }catch (Exception e) {
            log.error("短信发送结果入库异常",e);
       }
    }

    /**
     * 插入批量分割
     * @param phones
     * @param userId
     * @param channelCode
     * @param statusEnum
     */
    public void insetSplitBatch(String signature,String phones, int userId, String channelCode, SendStatusEnum statusEnum){
        try {

            List<String> strings = Arrays.asList(phones.split(","));
            List<List<String>> splitList = splitList(strings,5000);
            String batchCode = DateUtils.getDateTime();
            for(List<String> onlyList : splitList){
                List<NotesResultDTO> dataList = new ArrayList<>();
                for (String phone : onlyList) {
                    NotesResultDTO resultDTO = new NotesResultDTO();
                    resultDTO.setBatchCode(batchCode);
                    resultDTO.setChannelCode(channelCode);
                    resultDTO.setCreatedBy(Constants.PRODCUT_NAME);
                    resultDTO.setCreatedAt(new Date());
                    resultDTO.setPhone(phone);
                    resultDTO.setSendStatus(statusEnum.getStatus());
                    resultDTO.setUserId(userId);
                    resultDTO.setSignature(signature);
                    dataList.add(resultDTO);
                }
                notesResultMapper.insertBatchSelective(dataList);
            }
        }catch (Exception e) {
            log.error("短信发送结果入库异常",e);
        }
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param list
     * @return
     */
    public static List<List<String>> splitList(List<String> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<String>> result = new ArrayList<List<String>>();

        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<String> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

    public void insetBatch(String signature,NoteSendResDTO resDTO, int userId, String channelCode){
        try {
            List<NotesResultDTO> dataList = new ArrayList<>();
            for (NoteSendDetailResDTO detailResDTO : resDTO.getRespList()) {
                NotesResultDTO resultDTO = new NotesResultDTO();
                resultDTO.setBatchCode(detailResDTO.getMid());
                resultDTO.setChannelCode(channelCode);
                resultDTO.setCreatedBy(Constants.PRODCUT_NAME);
                resultDTO.setCreatedAt(new Date());
                resultDTO.setPhone(detailResDTO.getMobile());
                resultDTO.setSendStatus("0".equals(detailResDTO.getResultCode())?SendStatusEnum.SEND_SUCCESS.getStatus():SendStatusEnum.SEND_FAIL.getStatus());
                resultDTO.setUserId(userId);
                resultDTO.setSignature(signature);
                dataList.add(resultDTO);
            }
            notesResultMapper.insertBatchSelective(dataList);
        }catch (Exception e) {
            log.error("短信发送结果入库异常",e);
        }
    }

    public void insetBatch(String phoneType,String signature,String phones, int userId, String channelCode, SendStatusEnum statusEnum,String batchCode){
        try {

            List<String> strings = Arrays.asList(phones.split(","));
            List<NotesResultDTO> dataList = new ArrayList<>();
            for (String phone : strings) {
                NotesResultDTO resultDTO = new NotesResultDTO();
                resultDTO.setBatchCode(batchCode);
                resultDTO.setChannelCode(channelCode);
                resultDTO.setCreatedBy(Constants.PRODCUT_NAME);
                resultDTO.setCreatedAt(new Date());
                resultDTO.setPhone(phone);
                resultDTO.setSendStatus(statusEnum.getStatus());
                resultDTO.setUserId(userId);
                resultDTO.setSignature(signature);
                resultDTO.setPhoneType(phoneType);
                dataList.add(resultDTO);
            }
            notesResultMapper.insertBatchSelective(dataList);
        }catch (Exception e) {
            log.error("短信发送结果入库异常",e);
        }
    }
}