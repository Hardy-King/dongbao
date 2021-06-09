package com.msb.dongbao.controller.api;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.msb.dongbao.controller.api.posttest.SignDTO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api-safe")
public class ApiSafeController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    /**
     * get参数，防篡改
     * @param appId
     * @param name
     * @param sign 添加签名是为了参数(appId,name)防篡改
     * @param timestamp 添加时间戳是为了防止反复请求
     * @return
     */
    @RequestMapping("/get-test")
    public String getTest(String appId, String name, String sign, long timestamp, HttpServletRequest request) {

        HashMap<String,Object> map = new HashMap<>();
        // 参数写死，实际工作中是不能这么做的
//        map.put("appId",appId);
//        map.put("name",name);
//        map.put("timestamp",timestamp);

        // 获取get中的参数（应该写在filter或者interceptor中）
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            //get name
            String parameterName = parameterNames.nextElement();
            //get value
            String parameterValue = request.getParameter(parameterName);
            map.put(parameterName,parameterValue);
        }
        // 让接口在有效期内有效
       /* long time = System.currentTimeMillis()- timestamp;
        if(time > 1000*30) {
            return "接口过期了";
        }*/
        System.out.println(map);
        String s = CheckUtils.generatorSign(map);
        if (s.equals(sign)) {
            return "校验通过";
        }else {
            return "校验 不通过";
        }
    }

    @PostMapping("/post-test")
    public String postTest(@RequestBody SignDTO signDTO){

        JSONObject obj = JSONUtil.parseObj(signDTO);
        //1.参数转map
        Map<String, Object> stringObjectMap = Convert.toMap(String.class, Object.class, obj);
        //2.排序
        Map<String, Object> stringObjectMap1 = CheckUtils.sortMapByKey(stringObjectMap);
        //3.map生成签名
        Object signClient = stringObjectMap1.get("sign");
        String signServer = CheckUtils.generatorSign(stringObjectMap1);

        System.out.println("signServer:"+signServer+",signClient:"+signClient);
        //4.判断签名
        if (signServer.equals(signClient)) {
            return "校验通过";
        }else {
            return "校验 不通过";
        }
    }
}
