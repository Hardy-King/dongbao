package com.msb.dongbao.common.md5;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

public class EncoderOrDecoderTest {

    @Test
    public void encoder(){
        String sourceString = "123456";
        String s = DigestUtils.md5DigestAsHex(sourceString.getBytes());
        System.out.println("第一次加密的值"+s);
        String s2 = DigestUtils.md5DigestAsHex(sourceString.getBytes());
        System.out.println("第2次加密的值"+s2);
    }

    @Test
    public void bcrypt(){
        String sourceString = "123456";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String s = bCryptPasswordEncoder.encode(sourceString);
        boolean matches = bCryptPasswordEncoder.matches(sourceString, s);
        System.out.println("第一次加密的值"+s);
        System.out.println("第一次验证："+matches);

        String s2 = bCryptPasswordEncoder.encode(sourceString);
        boolean matches2 = bCryptPasswordEncoder.matches(sourceString, s2);
        System.out.println("第2次加密的值"+s2);
        System.out.println("第2次验证："+matches2);
    }
}
