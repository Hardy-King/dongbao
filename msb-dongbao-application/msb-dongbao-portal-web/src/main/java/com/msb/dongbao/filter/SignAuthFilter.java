package com.msb.dongbao.filter;

import com.alibaba.fastjson.JSONObject;
import com.msb.dongbao.Util.HttpParamUtils;
import com.msb.dongbao.controller.api.CheckUtils;
import com.msb.dongbao.util.BodyReaderHttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.SortedMap;

//@Component
public class SignAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 签名的验证
        //HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println(servletRequest.getParameter("name")+","+servletRequest.getParameter("sign")+","+servletRequest.getParameter("urlParam"));
        System.out.println("===================================================================================");
        HttpServletRequest request = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) servletRequest);
        System.out.println(request.getParameter("name")+","+request.getParameter("sign")+","+request.getParameter("urlParam"));
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取参数,统一get和post   不管url还是body
        //HttpParamUtils.getUrlParams(request);
        //HttpParamUtils.getBodyParams(request);
        SortedMap<String, String> allParams = HttpParamUtils.getAllParams(request);
        //校验签名
        boolean b = CheckUtils.checkSign(allParams);
        System.out.println("校验签名结果:"+b);
        if (b){
            // 校验签名
            filterChain.doFilter(request,response);
        }else {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json:charset=utf-8");
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
