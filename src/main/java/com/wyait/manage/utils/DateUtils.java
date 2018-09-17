package com.wyait.manage.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by BF100395 on 2017/5/6.
 */
public class DateUtils {
    private static final Logger logger = LoggerFactory
            .getLogger(DateUtils.class);
   public static final  String DATE_TYPE_1 ="yyyyMM";

    public static final  String DATE_TYPE_3 ="yyyy-MM";

   public static final  String DATE_TYPE_2="yyyy-MM-dd hh:mm:ss";
   public static final String DATE_TYPE_6="yyyy-MM-dd HH:mm:ss";
    public static final  String DATE_TYPE_4="yyyyMMddHHmmss";
    public static final String DATE_TYPE_5="yyyy-MM-dd";

    public static final String DATE_SHORT_FORMAT = "yyyyMMdd";

    /**
     * 获取当前时间
     * @return
     */
    public static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
        return formatter.format(new Date());
    }

    /**
     * 得到今天指定格式的日期
     *
     * @param format 日期格式
     * @return
     */
    public static String getToDay(String format) {
        return new DateTime().toString(StringUtils.isEmpty(format) ? DATE_SHORT_FORMAT : format);
    }

    public static  String getDateToStringType3(Date date,String type){
        String stringTime ="";
        if(date==null){
           return "";
        }
        try {
            stringTime = new SimpleDateFormat(type).format(date);
        }catch (Exception e){
            logger.error("时间转换异,异常:{}",e);
        }
        return  stringTime;
    }


   public static  String getDateToString(Date date,String type){
       String stringTime ="";
       if(date==null){
           date = new Date();
       }
       try {
           stringTime = new SimpleDateFormat(type).format(date);
       }catch (Exception e){
           logger.error("时间转换异,异常:{}",e);
       }
       return  stringTime;
   }
    public static  String format(Date date){
        String stringTime ="";
        if(date==null){
            date = new Date();
        }
        try {
            stringTime = new SimpleDateFormat(DATE_TYPE_6).format(date);
        }catch (Exception e){
            logger.error("时间转换异,异常:{}",e);
        }
        return  stringTime;
    }
   public static Date StringType1TODate(String dateString){
      if( StringUtils.isBlank(dateString)){
          logger.info("时间转换参数为空:{}",dateString);
          return null;
      }
       try {
           SimpleDateFormat sdf = new SimpleDateFormat(DATE_TYPE_1);
           Date date= sdf.parse(dateString);
           return  date;
       }catch (Exception e){
           logger.error("时间转换异常，请求:{},异常:{}",dateString,e);
           return null;
       }

   }


    public static Date StringType1TODate(String dateString,String dateType){
        if(StringUtils.isBlank(dateString)){
            logger.info("时间转换参数为空:{}",dateString);
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateType);
            Date date= sdf.parse(dateString);
            return  date;
        }catch (Exception e){
            logger.error("时间转换异常，请求:{},异常:{}",dateString,e);
            return null;
        }

    }



    /**
     *
     * @param dateBefor
     * @param dateAfter
     * @return
     */
   public static boolean compareDate(Date dateBefor,Date dateAfter){
       if(dateBefor ==null || dateAfter ==null){
           logger.info("时间比对参数为空：dateBefor:{},dateAfter:{}",dateBefor,dateAfter);
           return false;
       }
       if(dateAfter.after(dateBefor)){
         return true;
       }else {
           return false;
       }
   }

    /**
     * 计算两个日期之间的天数
     * @param dateBefor
     * @param dateAfter
     * @return
     */
   public static int daysOfTwo(Date dateBefor,Date dateAfter){
       Calendar calendar = Calendar.getInstance();
       calendar.setTime(dateBefor);
       int befor = calendar.get(Calendar.DAY_OF_YEAR);
       calendar.setTime(dateAfter);
       int end = calendar.get(Calendar.DAY_OF_YEAR);
       return end - befor;

   }

    public static void main(String[] args) {
        Date datebefore = DateUtils.StringType1TODate("2017-08-01");
        Date  dateafter  = DateUtils.StringType1TODate("2017-08-04");
        try {
            System.out.println(DateUtils.daysOfTwo(datebefore,dateafter));
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
