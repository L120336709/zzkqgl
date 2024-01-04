package com.ruoyi.web.controller.zzhtml;

import com.alibaba.fastjson.JSONObject;

public class ApiController {

    public static String URL = "http://schoolspace.eszedu.com/api/";
    //正式环境  schoolspace.eszedu.com
    //测试环境  10.0.40.181:7010
    //职中 10010308    测试环境下有数据的 10008530

    //arcticleList 文章类  newsList 新闻类  picList 图片

    //获取新闻类数据
    public static JSONObject getMessage(String url,String orgId,String nodeId,Integer page,Integer pageSize){
        URL = "http://schoolspace.eszedu.com/api/";  url = URL+url;
        url = url + "?orgId="+orgId;
        url = url + "&nodeId="+nodeId;
        url = url + "&page=" + page;
        url = url + "&pageSize=" + pageSize;
        System.err.println(url);
        String result = ZPTinterface.sendPost(url, "");
        System.err.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);

        return jsonObject;
    }












}
