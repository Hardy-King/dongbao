package com.msb.dongbao.controller.api;

import com.msb.dongbao.Util.MD5Util;
import com.msb.dongbao.Util.Sha256Utils;

import java.util.*;

public class CheckUtils {

    public static String appSecret = "aaa";

    //根据map生成签名
    public static String generatorSign(Map<String,Object> map) {
        if (map.get("sign")!=null) {
            map.remove("sign");
        }
        //排序
        Map<String,Object> stringObjectMap = sortMapByKey(map);
        //转格式：name=zhangsan&age=10，：name，张三，age，10
        Set<Map.Entry<String, Object>> entries = stringObjectMap.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : entries) {
            sb.append(entry.getKey()+","+ entry.getValue()).append("#");
        }
        //组装secret，在参数的后面添加secret
        sb.append("secret").append(appSecret);
        //生成签名
        //return MD5Util.md5(sb.toString());
        System.out.println("gen1:"+Sha256Utils.getSHA256(sb.toString()));
        return Sha256Utils.getSHA256(sb.toString());
    }

    public static Map<String,Object> sortMapByKey(Map<String,Object> map) {
        //判断一下map是否为空，自己写
        Map<String,Object> sortMap = new TreeMap<>(new MyMapComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    static class MyMapComparator implements Comparator<String> {
        public int compare(String o1,String o2) {
            return o1.compareTo(o2);
        }
    }

    public static void main(String[] args) {
        HashMap map = new HashMap();
        /*map.put("dee",1);
        map.put("tre",2);
        map.put("asd",3);
        Map map1 = sortMapByKey(map);
        System.out.println(map1);*/

        map.put("appId",1);
        map.put("name",2);
        //map.put("timestamp",1623157434000L);

        String s = generatorSign(map);
        System.out.println(s);//MD5签名：a3337ee5d708e41ac38bd0d88561b95f
        //sha256签名：f8aa720c569bc46deba2a23e7ce257b6d8412d2cd2396cac5c01634718f86c4d
    }
}
