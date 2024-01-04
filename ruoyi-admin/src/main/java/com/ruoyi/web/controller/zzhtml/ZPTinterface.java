package com.ruoyi.web.controller.zzhtml;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.security.Md5Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class ZPTinterface {

    public static String YKTURL = "http://auth.eszedu.com/sso/accountLogin";

    public static String YKTURL2 = "http://edu.eszedu.com/v1/user/info/accessToken";

    public static String GradeURL = "http://edu.eszedu.com/v1/school/grade/list";

    public static String ClassURL = "http://edu.eszedu.com/v1/school/class/list";

    public static String StudentURL2 = "http://edu.eszedu.com/v1/school/student/list";

    public static String StudentURL = "http://edu.eszedu.com/v1/class/student/list";

    public static String TeacherURL = "http://edu.eszedu.com/v1/school/teacher/list";



    public static String TBstu = "http://edu.eszedu.com/v1/syn/school/grade/list";


    public static String TBClass = "http://edu.eszedu.com/v1/syn/school/class/list";


    public static JSONObject accLogin(String userName,String passWord){
        Date now = new Date();
        String time=DateFormatUtils.format(now, "yyyyMMddHHmmss") ;

        ArrayList<String> signs = new ArrayList<>();
        signs.add("e7d4eb2b3cbf4a22945d475a23bae0d8");
        signs.add(time);
        signs.add(userName);
        signs.add(passWord);
        String sign = getSign(signs);
        String url = YKTURL;
        url = url + "?appKey=e7d4eb2b3cbf4a22945d475a23bae0d8";
        url = url + "&timestamp="+time;
        url = url + "&sign=" + sign;
        url = url + "&userKey=" + userName;
        url = url + "&userPassword=" + passWord;
        System.err.println(url);
        String result = sendPost(url, "");
        System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;

    }

    public static JSONObject getUserinfo(String accessToken) {
        Date now = new Date();
        String time = DateFormatUtils.format(now, "yyyyMMddHHmmss");
        ArrayList<String> signs = new ArrayList<>();
        signs.add("e7d4eb2b3cbf4a22945d475a23bae0d8");
        signs.add(time);
        signs.add(accessToken);
        String sign = getSign(signs);
        String url = YKTURL2;
        url = url + "?appKey=e7d4eb2b3cbf4a22945d475a23bae0d8";
        url = url + "&timestamp=" + time;
        url = url + "&sign=" + sign;
        url = url + "&accessToken=" + accessToken;
        System.err.println(url);
        String result = sendPost(url, "");
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public static JSONObject getStudent(String accessToken,String classId) {
        Date now = new Date();
        String time = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
        String time2 = DateFormatUtils.format(now, "yyyyMMddHHmmss");
        System.err.println(time);
        ArrayList<String> signs = new ArrayList<>();
        signs.add("accessToken"+accessToken);
        signs.add("appKeye7d4eb2b3cbf4a22945d475a23bae0d8");
        signs.add("classId"+classId);
        signs.add("timestamp"+time);
        String sign = getSign(signs);
        String url = StudentURL;
        url = url + "?appKey=e7d4eb2b3cbf4a22945d475a23bae0d8";
        url = url + "&timestamp=" + time;
        url = url + "&sign=" + sign;
        url = url + "&accessToken=" + accessToken;
        url = url + "&classId=" + classId;
        System.err.println(url);
        String result = sendPost(url, "");
        System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public static JSONObject getStudent2(String accessToken,String schoolId) {
        Date now = new Date();
        String time = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
        //String time = DateFormatUtils.format(now, "yyyyMMddHHmmss");
        ArrayList<String> signs = new ArrayList<>();
        signs.add("e7d4eb2b3cbf4a22945d475a23bae0d8");
        signs.add(time);
        signs.add(accessToken);
        signs.add(schoolId);
        String sign = getSign(signs);
        String url = StudentURL2;
        url = url + "?appKey=e7d4eb2b3cbf4a22945d475a23bae0d8";
        url = url + "&timestamp=" + time;
        url = url + "&sign=" + sign;
        url = url + "&accessToken=" + accessToken;
        url = url + "&schoolId=" + schoolId;
        System.err.println(url);
        String result = sendPost(url, "");
        System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public static JSONObject TBstu(String accessToken,String schoolId) {
        Date now = new Date();
        String time = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
        //String time = DateFormatUtils.format(now, "yyyyMMddHHmmss");
        ArrayList<String> signs = new ArrayList<>();
        signs.add("appKeye7d4eb2b3cbf4a22945d475a23bae0d8");
        signs.add("schoolId"+schoolId);
        signs.add("timestamp"+time);
        //signs.add(accessToken);

        String sign =  getSign(signs);
        String url = TBClass;
        url = url + "?appKey=e7d4eb2b3cbf4a22945d475a23bae0d8";
        url = url + "&timestamp=" + time;
        url = url + "&sign=" + sign;
        //url = url + "&accessToken=" + accessToken;
        url = url + "&schoolId=" + schoolId;
        System.err.println(url);
        String result = sendPost(url, "");
        System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }


    public static JSONObject getTeacher(String accessToken,String schoolId) {
        String result=getRes( accessToken, schoolId,TeacherURL);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public static String getRes(String accessToken,String schoolId,String url){
        Date now = new Date();
        String time = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
        ArrayList<String> signs = new ArrayList<>();
        signs.add("accessToken"+accessToken);
        signs.add("appKeye7d4eb2b3cbf4a22945d475a23bae0d8");
        signs.add("schoolId"+schoolId);
        signs.add("timestamp"+time);
        String sign = getSign(signs);
        url = url + "?appKey=e7d4eb2b3cbf4a22945d475a23bae0d8";
        url = url + "&timestamp=" + time;
        url = url + "&sign=" + sign;
        url = url + "&accessToken=" + accessToken;
        url = url + "&schoolId=" + schoolId;
        System.err.println(url);
        String result = sendPost(url, "");
        System.err.println(result);
        return result;
    }


    public static JSONObject getClass(String accessToken,String schoolId){
        String result=getRes( accessToken, schoolId,ClassURL);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }


    public static JSONObject getGrade(String accessToken,String schoolId) {
        String result=getRes( accessToken, schoolId,GradeURL);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }


    public static String getSign(ArrayList<String> signs) {
        String canshu = "ab46a20998054147b380823bf0a74ba2";
        for (int i = 0; i < signs.size(); i++) {
            canshu = canshu + signs.get(i);
        }
        System.err.println(canshu);
        String sign = Md5Utils.hash(canshu).toUpperCase();
        System.err.println(sign);

        //String sign = MD5.create().digestHex(canshu).toUpperCase(Locale.ROOT);
        return sign;
    }



    public static String sendPost(String url, String param) {
        String result = "";
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        CloseableHttpResponse response = null;
        try {
            HttpPost httppost = new HttpPost(url);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            BasicHttpEntity requestBody = new BasicHttpEntity();
            requestBody.setContent(new ByteArrayInputStream(param.getBytes("UTF-8")));
            requestBody.setContentLength(param.getBytes("UTF-8").length);

            httppost.setEntity(requestBody);
            // 执行客户端请求
            response = httpClient.execute(httppost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
            //自动释放链接
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            // httpclient.getConnectionManager().shutdown();
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
