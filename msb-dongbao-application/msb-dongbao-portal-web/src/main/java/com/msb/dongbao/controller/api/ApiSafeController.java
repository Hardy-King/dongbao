package com.msb.dongbao.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api-safe")
public class ApiSafeController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/get-test")
    public String getTest(String appId,String name, String sign) {

        HashMap<String,Object> map = new HashMap<>();
        map.put("appId",appId);
        map.put("name",name);

        String s = CheckUtils.generatorSign(map);
        if (s.equals(sign)) {
            return "校验通过";
        }else {
            return "校验 不通过";
        }
    }
}
