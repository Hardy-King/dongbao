package com.msb.dongbao.filter;

import com.alibaba.fastjson.JSONObject;
import com.msb.dongbao.Util.HttpParamUtils;
import com.msb.dongbao.controller.api.CheckUtils;
import com.msb.dongbao.util.BodyReaderHttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;

@Component
public class SignAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 签名的验证
        //HttpServletRequest request = (HttpServletRequest) servletRequest;

        System.out.println("===================================================================================");
        // 复制request，因为此处被流吸走了，就不走业务层了
        HttpServletRequest request = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        /**
         * 获取request中body参数
         */
//        BufferedReader br = request.getReader();
//        String str = "";
//        String listString = "";
//        while ((str=br.readLine())!=null) {
//            listString+=str;
//        }
//        System.out.println("body-dofilter:"+listString);
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取参数,统一get和post   不管url还是body
        //HttpParamUtils.getUrlParams(request);
        //HttpParamUtils.getBodyParams(request);
        SortedMap<String, Object> allParams = HttpParamUtils.getAllParams(request);
        System.out.println("filter:"+allParams);
        //校验签名
        boolean b = CheckUtils.checkSign(allParams);
        System.out.println("校验签名结果:"+b);
        if (b){
            // 校验签名
            filterChain.doFilter(request,response);
        }else {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            JSONObject param = new JSONObject();
            param.put("code",-1);
            param.put("message", "签名错了");

            writer.append(param.toJSONString());
        }
        //

        System.out.println("filter生效了");
    }

    @Override
    public void destroy() {
        System.out.println("销毁...");
    }
}
