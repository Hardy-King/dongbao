package com.msb.dongbao.custom;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaRenderException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_DATE;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

/**
 * 谷歌默认验证码组件
 *
 * @author GK
 */
@Component
@Slf4j
public class MyGoogleKaptcha implements Kaptcha {

    private DefaultKaptcha kaptcha;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public MyGoogleKaptcha(DefaultKaptcha kaptcha) {
        this.kaptcha = kaptcha;
    }

    @Override
    public String render() {
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate");
        response.addHeader(HttpHeaders.CACHE_CONTROL, "post-check=0, pre-check=0");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setContentType("image/jpeg");
        String sessionCode = kaptcha.createText();
        try (ServletOutputStream out = response.getOutputStream()) {

            // 放到Redis
            stringRedisTemplate.opsForValue().set("gk",sessionCode);

            ImageIO.write(kaptcha.createImage(sessionCode), "jpg", out);
            return sessionCode;
        } catch (IOException e) {
            throw new KaptchaRenderException(e);
        }
    }

    @Override
    public boolean validate(String code) {
        return validate(code, 900);
    }

    @Override
    public boolean validate(@NonNull String code, long second) {
        String sessionCode = stringRedisTemplate.opsForValue().get("gk");
        if (code.equals(sessionCode)) {
            stringRedisTemplate.delete("gk");
            return true;
        }else {
            return false;
        }

    }

}

