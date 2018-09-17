package com.wyait.manage.channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */
public abstract class BaseNoteApi {

    /**
     * 渠道功能
     * @return
     */
    public abstract String getChannelCode();

    /**
     * 发送短信
     * @return
     */
    public abstract void sendNotes(String signature,String content, List<String> phones,String phoneType);

    /**
     * 渠道包分割大小
     * @return
     */
    public abstract int packetSize();

    /**
     * 分割list
     *
     * @param list
     * @return
     */
    public static List<String> splitList(List<String> list,int packetSize) {
        String phones = "";
        if (list.size() <= packetSize) {
            for (String phone : list) {
                phones += phone + ",";
            }
            return Arrays.asList(phones.substring(0, phones.length() - 1));
        }

        List<String> phoneList = new ArrayList<>();
        for (int i = 0, size = list.size(); i < size; i++) {

            if (i == size - 1) {
                phones += list.get(i);
                phoneList.add(phones);
                return phoneList;
            }
            if (i % packetSize == 0 && i != 0) {
                phoneList.add(phones.substring(0, phones.length() - 1));
                phones = "";
            }
            phones += list.get(i) + ",";

        }
        return phoneList;
    }
}
