package com.wyait.manage.utils.http.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 人脸
 * @user: zyd
 * @date: 2016/9/7
 * @time: 18:55
 * @version: 1.0.0
 */
@Getter
@Setter
@ToString
public class FaceBean implements Serializable{
    private static final long serialVersionUID = 5605069062985470062L;
    private String filename = "image";
    private String faceKeyName = "image";
    /**
     * 图片二进制流
     */
    private byte[] img;
}
