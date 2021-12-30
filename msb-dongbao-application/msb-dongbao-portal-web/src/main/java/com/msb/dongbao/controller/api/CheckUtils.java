package com.msb.dongbao.controller.api;

import com.msb.dongbao.Util.MD5Util;
import com.msb.dongbao.Util.Sha256Utils;

import java.util.*;

public class CheckUtils {

    public static String appSecret = "aaa";

    public static boolean checkSign(Map<String,Object> map) {
        String sign = (String)map.get("sign");
        map.remove("sign");
        //生成sign
        String s = CheckUtils.generatorSign(map);
        if (s.equals(sign)) {
            return true;
        }else {
            return false;
        }
    }
    //根据map生成签名
    public static String generatorSign(Map<String,Object> map) {
        if (map.get("sign")!=null) {
            map.remove("sign");
        }
        //1.排序 按照字典顺序排序
        Map<String,Object> stringObjectMap = sortMapByKey(map);
        //2.转格式：name=zhangsan&age=10，----->  name，张三，age，10
        Set<Map.Entry<String, Object>> entries = stringObjectMap.entrySet();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : entries) {
            sb.append(entry.getKey()+","+ entry.getValue()).append("#");
        }
        //sb.subSequence(0,sb.length()-1);
        //3.组装secret，在参数的后面添加secret
        sb.append("secret").append(appSecret);
        //4.生成签名
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
        map.put("appId","1");
        map.put("name","2");
        map.put("urlParam","3");
        //map.put("timestamp",1623917842000L);
        Map map1 = sortMapByKey(map);
        System.out.println(map1);

//        map.put("appId",1);
//        map.put("name",2);


        String s = generatorSign(map);
        System.out.println(s);//MD5签名：a3337ee5d708e41ac38bd0d88561b95f
        //sha256签名：f8aa720c569bc46deba2a23e7ce257b6d8412d2cd2396cac5c01634718f86c4d
    }
}
