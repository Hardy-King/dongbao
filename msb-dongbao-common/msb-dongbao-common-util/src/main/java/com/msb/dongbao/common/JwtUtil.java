package com.msb.dongbao.common;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtil {

    public static final String secret = "asdfasdf";

    public static String createToken(String subject){
        String token = Jwts.builder().setSubject(subject).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 100)).//token的生效时间为3秒
                signWith(SignatureAlgorithm.HS256,secret).
                compact();
        return token;
    }

    public static String parseToken(String token){
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        String subject = body.getSubject();
        return subject;
    }

    public static void main(String[] args) throws InterruptedException {
        double t1 = System.currentTimeMillis();
        System.out.println(t1);
        String name = "教育";
        String token = createToken(name);
        System.out.println("token: " + token);

        String s = parseToken(token);
        System.out.println("name:" + s);
        double t2 = System.currentTimeMillis();
        System.out.println(t2);
        System.out.println(t2-t1);

        TimeUnit.SECONDS.sleep(4);
        s = parseToken(token);
        System.out.println("4秒后：" +s);
    }
}
