package com.msb.dongbao.Util;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
public class HttpParamUtils {
    /**
     * 获取所有参数，包括url和body
     * @param request
     * @return
     * @throws IOException
     */
    public static SortedMap<String,String> getAllParams(HttpServletRequest request) throws IOException {
        //获取url上的参数
        Map<String, String> urlParams = getUrlParams(request);
        System.out.println(urlParams);

        //获取body上的参数
        Map<String, String> bodyParams = getBodyParams(request);
        //总的参数map
        SortedMap<String,String> allMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : urlParams.entrySet()) {
            allMap.put((String) entry.getKey(),(String) entry.getValue());
        }
        for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
            allMap.put((String) entry.getKey(),(String) entry.getValue());
        }
        log.info("所有参数："+allMap);
        return allMap;
    }


    /**
     * 获取body参数
     * @param request
     * @return
     */
    public static Map<String,String> getBodyParams(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        //读取流
        String s = "";
        while ((s=reader.readLine())!=null) {
            sb.append(s);
        }

        //转map
        Map map = JSONObject.parseObject(sb.toString(),Map.class);
        log.info("获取body参数："+map);
        return map;
    }


    /**
     * 获取url中的参数  /asdf/asd?a=b&b=c
     * @param request
     * @return
     */
    public static Map<String,String> getUrlParams(HttpServletRequest request) {
        String queryParam = "";
        try {
            queryParam = URLDecoder.decode(request.getQueryString(),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Map<String,String> result = new HashMap<>();
        String[] split = queryParam.split("&");
        for (String s : split) {
            int i = s.indexOf("=");
            result.put(s.substring(0,i),s.substring(i+1));
        }
        System.out.println("获取url 参数："+result);
        return result;

    }
}
