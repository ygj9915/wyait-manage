package com.wyait.manage.utils.http;

/**
 * @User: Liangsongzhu.
 * @Date: 2017/3/28.
 * @Time: 15:32.
 * @Version: 1.0.0
 * @Description: 通用常量
 */
public class Constants {

    //签名算法HmacSha256
    public static final String HMAC_SHA256 = "HmacSHA256";
    //编码UTF-8
    public static final String EN_CODING = "UTF-8";
    //换行符
    public static final String LF = "\n";
    //串联符
    public static final String SPE1 = ",";
    //示意符
    public static final String SPE2 = ":";
    //连接符
    public static final String SPE3 = "&";
    //赋值符
    public static final String SPE4 = "=";
    //问号符
    public static final String SPE5 = "?";
    //默认请求超时时间,单位毫秒
    public static final int DEFAULT_TIMEOUT = 5000;
    //默认请求等待时间,单位毫秒
    public static final int DEFAULT_READ_TIMEOUT = 10000;
    //默认请求等待时间,单位毫秒
    public static final int DEFAULT_REQUEST_TIMEOUT = 10000;

    public static final String CODE_SUCCESS = "1";
    public static final String CODE_REFUND = "2";
    public static final String CODE_FAIL = "0";
    public static final String CODE_EXCEPTION = "-1";

    public static final String MESSAGE_SUCCESS = "成功";
    public static final String MESSAGE_FAIL = "失败";
    public static final String MESSAGE_EXCEPTION = "异常";

    public static final String SUCCESS_CODE = "00";//验证成功
    public static final String FAIL_CODE = "99";//验证失败

    public static final String SUCCESS_CODE_5_0 = "000000";//验证成功

    public static final Integer FEE_FLAG_YES = 1;//收费
    public static final Integer FEE_FLAG_NO = 0;//不收费
}
