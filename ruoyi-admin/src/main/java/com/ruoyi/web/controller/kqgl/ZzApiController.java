package com.ruoyi.web.controller.kqgl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.security.Md5Utils;
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

public class ZzApiController {

    public static String GradeURL = "http://edu.eszedu.com/v1/syn/school/grade/list";
    public static String ClassURL = "http://edu.eszedu.com/v1/syn/school/class/list";

    public static String TeacherURL = "http://edu.eszedu.com/v1/syn/school/user/list";

    public static String StudentURL = "http://edu.eszedu.com/v1/syn/school/student/list";

    public static JSONObject getStudent(String schoolId,int pageNo,int pageSize) {
        String result = getRes(schoolId, StudentURL,pageNo,pageSize);
      //  System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }



    public static JSONObject getTeacher(String schoolId,int pageNo,int pageSize) {
        String result = getRes(schoolId, TeacherURL,pageNo,pageSize);
        //System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public static JSONObject getClass(String schoolId) {
        String result = getRes(schoolId, ClassURL,1,1000);
        //System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }

    public static JSONObject getGrade(String schoolId) {
        String result = getRes(schoolId, GradeURL,1,1000);
       // System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }


    public static String getRes(String schoolId, String url,int pageNo,int pageSize) {
        Date now = new Date();
        String time = DateFormatUtils.format(now, "yyyyMMddHHmmssSSS");
        ArrayList<String> signs = new ArrayList<>();
        signs.add("appKeyb156886e954a49bd88ebf8a2a742e45f");

        signs.add("pageNo"+pageNo);
        signs.add("pageSize"+pageSize);

        signs.add("schoolId" + schoolId);
        signs.add("timestamp" + time);

        String sign = getSign(signs);
        url = url + "?appKey=b156886e954a49bd88ebf8a2a742e45f";
        url = url + "&pageNo="+pageNo;
        url = url + "&pageSize="+pageSize;
        url = url + "&timestamp=" + time;
        url = url + "&sign=" + sign;
        url = url + "&schoolId=" + schoolId;
        String result = sendPost(url, "");
        System.err.println(result);
        return result;
    }

    public static String getSign(ArrayList<String> signs) {
        String canshu = "c6ef7ebc9f1342ac925bd08a1b208ebf";
        for (int i = 0; i < signs.size(); i++) {
            canshu = canshu + signs.get(i);
        }
        System.err.println(canshu);
        String sign = Md5Utils.hash(canshu).toUpperCase();
       // System.err.println(sign);

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
