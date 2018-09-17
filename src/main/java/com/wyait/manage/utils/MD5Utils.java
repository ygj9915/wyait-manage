package com.wyait.manage.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author yeguojian
 * @date on 2018/9/11
 * @description
 */
@Slf4j
public class MD5Utils {

    public static String md5(String message, String chartset) {
        return DigestUtils.md5Hex(getContentBytes(message, chartset));
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            log.error("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
            return null;
        }
    }
}
