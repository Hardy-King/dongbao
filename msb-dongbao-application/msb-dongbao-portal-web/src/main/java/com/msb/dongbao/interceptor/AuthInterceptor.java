package com.msb.dongbao.interceptor;


import com.msb.dongbao.common.JwtUtil;
import com.msb.dongbao.common.base.TokenException;
import com.msb.dongbao.common.base.annotations.TokenCheck;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器进入");
        String token = request.getHeader("token");
        System.out.println("token:"+token);
        if(StringUtils.isBlank(token)){
            throw new TokenException("token 为空");
            //return false;
        }
        /**
         * handler是处理Controller类中edit方法的
         */
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(TokenCheck.class)) {
            TokenCheck annotation = method.getAnnotation(TokenCheck.class);
            if (annotation.required()) {
                //校验token
                try {
                    String s = JwtUtil.parseToken(token);
                    System.out.println("解析token："+s);
                    return true;
                }catch (Exception e) {
                    throw new TokenException("token 解析异常");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle========");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion========");
    }
}
