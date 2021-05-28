package com.msb.dongbao.common.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.msb.dongbao.common.base.annotations.TokenCheck;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/k-captcha")
public class KCaptchaController {

    @Autowired
    private Kaptcha kaptcha;

    @GetMapping("/generator")
    @TokenCheck(required = false)
    public void generatorCode(HttpServletRequest request, HttpServletResponse response) {
        kaptcha.render();
    }
    @GetMapping("/verify")
    @TokenCheck(required = false)
    public String verify(String verifyCode, HttpServletRequest request) {

        try {
            Boolean aBoolean = kaptcha.validate(verifyCode);
            if (aBoolean){

                return "通过";
            }
        }catch (KaptchaNotFoundException e) {
            e.printStackTrace();
        }

        return "不通过";
    }

}