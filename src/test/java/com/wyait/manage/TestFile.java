package com.wyait.manage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yeguojian
 * @date on 2018/7/9
 * @description
 */
public class TestFile {
    public static void main(String[] args) {
        File csv = new File("/Users/ygj/me/phone/test");  // CSV文件路径
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
            List<String> allString = new ArrayList<>();
            int i=1;
            while ((line = br.readLine()) != null)  //读取到的内容给line变量
            {
                everyLine = line;
                allString.add(everyLine);
                i++;
                System.err.println("当前列号："+i);
            }
            System.out.println("csv表格中所有行数："+allString.size());

            Thread.currentThread();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
