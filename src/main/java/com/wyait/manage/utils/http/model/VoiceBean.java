package com.wyait.manage.utils.http.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 声音
 * @user: zyd
 * @date: 2016/9/7
 * @time: 18:55
 * @version: 1.0.0
 */
@Getter
@Setter
@ToString
public class VoiceBean implements Serializable {
    private static final long serialVersionUID = 6343256472904960855L;
    private String filename="speech";
    private String voiceKeyName="speech";
    /**
     * 语音二进制数据
     */
    private byte[] voiceData;
    private String voiceTxtKeyName="content";
    /**
     * 语音内容
     */
    private String voiceTxt;
}
