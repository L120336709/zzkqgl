package com.ruoyi.web.controller.tool;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @Author whj
 * @Date 2019-01-16 10:59
 * @Version 1.0
 */
public class Utils {
    //写一个md5加密的方法
    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * 将时间戳转时间10位
     * @param s
     * @return
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s)*1000;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    public static Date StringToData(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = simpleDateFormat.parse(s);
        } catch (Exception e) {
            return null;
        }
        return date;
    }


    /**
     * 获取int型的时间戳,单位秒
     *
     * @return
     */
    public static Integer getTimeInt() {
        Integer time = 0;
        time = (int) ((new Date()).getTime() / 1000l);
        return time;
    }

    public static String sign(Map<String, String> paramValues, String secret) {
        String sign = null;
        StringBuilder sb = new StringBuilder();
        List<String> paramNames = new ArrayList<String>(paramValues.size());
        paramNames.addAll(paramValues.keySet());
        Collections.sort(paramNames);
        sb.append(secret);
        for (String paramName : paramNames) {
            sb.append(paramName).append(paramValues.get(paramName));
        }
        sign = md5(sb.toString());
        return sign;
    }

    /**
     * 将时间转换为时间戳
     *
     * @param time   格式化时间戳
     * @param format 格式 yyyy-MM-dd HH:mm:ss
     * @return ts 时间戳，毫秒
     */
    public static Long dateToStamp(String time, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = simpleDateFormat.parse(time);
        long ts = date.getTime();
        return ts;
    }

    /**
     * 将时间转换为时间戳
     *
     * @param
     * @param format 格式 yyyy-MM-dd HH:mm:ss
     * @return ts 时间戳，毫秒
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * @param minDate 最小时间  2015-01
     * @param maxDate 最大时间 2015-10
     * @return 日期集合 格式为 年-月
     */
    public static List<String> getMonthBetween(Date minDate, Date maxDate, String pattern) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

}
