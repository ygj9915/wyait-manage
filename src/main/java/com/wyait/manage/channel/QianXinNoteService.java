package com.wyait.manage.channel;

import com.wyait.manage.channel.api.QianxinDataChannelApi;
import com.wyait.manage.channel.model.qianxin.QianXinNoteReqDTO;
import com.wyait.manage.enums.ChannelCodeEnums;
import com.wyait.manage.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * @author yeguojian
 * @date on 2018/8/5
 * @description
 */
@Service
@Slf4j
public class QianXinNoteService extends BaseNoteApi {

    private static final String USERNAME = "960009";

    private static final String PASSWORD = "FGa69wr3j30k5LeQ8sEU7y2C";

    @Autowired
    private QianxinDataChannelApi qianxinDataChannelApi;

    @Override
    public String getChannelCode() {
        return ChannelCodeEnums.QX.getCode();
    }

    @Override
    public void sendNotes(String signature, String content, List<String> phones, String phoneType) {
        for(String sendPhone : phones){
            sendNote(signature,content,sendPhone);
        }
    }

    @Override
    public int packetSize() {
        return 1000;
    }

    /**
     *
     * @param content
     * @param phones
     */
    private void sendNote(String signature, String content, String phones){
        try {
            QianXinNoteReqDTO reqDTO = new QianXinNoteReqDTO();
            reqDTO.setUserid("16");
            reqDTO.setTimestamp(DateUtils.getDateToString(new Date(), DateUtils.DATE_TYPE_4));
            reqDTO.setSign(getSign(reqDTO.getTimestamp()));
            reqDTO.setMobile(phones);
            reqDTO.setContent(content);
            reqDTO.setAction("send");
            Call<Object> call = qianxinDataChannelApi.sendQinXinNote(reqDTO);
            Response<Object> response = call.execute();
            if (response.code() == 200) {
                Object result = response.body();
                log.info("请求千信渠道结果", result);
            }
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }catch (Exception e){

        }
    }

    /**
     * 获取签名
     * @param timestamp 时间戳
     * @return 获取签名
     */
    private static String getSign(String timestamp) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return getMD5(USERNAME+PASSWORD+timestamp);
    }

    public static String getMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes("UTF-8"));
        return new BigInteger(1, md.digest()).toString(16);
    }

}
