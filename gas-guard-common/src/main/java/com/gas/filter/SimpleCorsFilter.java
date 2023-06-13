package com.gas.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Order(value = 0)
@Component
public class SimpleCorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    /**
     * 默认是发送请求的时候不带cookie，需要通过设置withCredentials: true。 这个时候需要注意需要后端配合设置
     * header信息 Access-Control-Allow-Credentials:true
     * Access-Control-Allow-Origin不可以为 ''，因为 '' 会和 Access-Control-Allow-Credentials:true 冲突，需配置指定的地址
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");
        if (!StringUtils.hasText(origin)) {
            origin = "*";
        }
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "DELETE, PUT, HEAD, POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token,Authorization");
        response.setHeader("Access-Control-Expose-Headers", "Content-disposition,Content-Type,Access-Token,Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpStatus.OK.value());
            return;
        }
        chain.doFilter(req, res);
    }
}
