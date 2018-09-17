package com.wyait.manage;

import com.wyait.manage.channel.*;
import com.wyait.manage.enums.PhoneTypeEnum;
import com.wyait.manage.utils.HttpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;

/**
 * @author yeguojian
 * @date on 2018/7/8
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteTest {

    @Autowired
    public HttpService httpService;

    @Autowired
    private AnHuiGongAnNoteServices anHuiGongAnNoteServices;

    @Autowired
    private ShangHaiBigDataNoteServices shangHaiBigDataNoteServices;

    @Autowired
    private SaiTianNoteServices juTianNoteServices;

    @Autowired
    private QianXinNoteService qianXinNoteService;
    @Autowired
    private LiuliangTongNoteService liuliangTongNoteService;
    @Autowired
    private RongLianNoteService rongLianNoteService;

    private static final String fileUrl = "/Users/ygj/13478787811.csv";

    @Test
    public void test() throws IOException {
        /*Map<String, Object> map = new HashMap<>();
        map.put("username", "james");
        map.put("userkey", "12qw34er@");
        HttpResult result =httpService.doPost("http://api3.data2share.com:13580/sms/Token/getApiToken", map);

        System.err.println(result.getBody());*/
        Set<String> set = readFile();
//        13795293168;

        //758707  万通宝
        //758706  包有钱
        //758704  万达

        anHuiGongAnNoteServices.sendNotes("万达","758704",Arrays.asList("18701755054"), PhoneTypeEnum.YD.getType());
    }

    @Test
    public void testLiuLiangTong(){
        liuliangTongNoteService.sendNotes("测试","test", Arrays.asList("16602146993"),PhoneTypeEnum.YD.getType());
    }

    @Test
    public void testRongLianNote(){
        rongLianNoteService.sendNotes("测试","1", Arrays.asList("18701755054"),PhoneTypeEnum.YD.getType());
    }


    public static Set<String> readFile() {
        File csv = new File(fileUrl);  // CSV文件路径
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(csv));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        String line = "";
        String everyLine = "";
        try {
            Set<String> allString = new HashSet<>();
            int i=1;
            while ((line = br.readLine()) != null)  //读取到的内容给line变量
            {
                everyLine = line;
                i++;
                System.err.println("当前列号："+i);

                allString.add(everyLine);
            }
            System.out.println("csv表格中所有行数："+allString.size());

            return allString;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
