package com.wyait.manage.utils.http.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @User: Liangsongzhu.
 * @Date: 2017/3/28.
 * @Time: 15:24.
 * @Version: 1.0.0
 * @Description: HTTP请求返回
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClientResponse implements Serializable{
    private static final long serialVersionUID = -2533008546249094426L;

    //返回码
    private int statusCode;
    //返回类型
    private String contentType;
    //返回 REQUEST_ID
    private String requestId;
    //错误描述
    private String errorMessage;
    //返回报文头
    private Map<String, String> headers;
    //返回报文体
    private String body;

    public String getHeader(String key) {
        if (null != headers) {
            return headers.get(key);
        } else {
            return null;
        }
    }

    public void setHeader(String key, String value) {
        if (null == this.headers) {
            this.headers = new HashMap<String, String>();
        }
        this.headers.put(key, value);
    }
}
