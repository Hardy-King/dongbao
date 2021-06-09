package com.msb.dongbao;

import com.msb.dongbao.Util.HttpParamUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SignAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 签名的验证
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取参数,统一get和post   不管url还是body
        HttpParamUtils.getUrlParams(request);
        // 校验签名
        filterChain.doFilter(request,response);
        //

        System.out.println("filter生效了");
    }

    @Override
    public void destroy() {
        System.out.println("销毁...");
    }
}
