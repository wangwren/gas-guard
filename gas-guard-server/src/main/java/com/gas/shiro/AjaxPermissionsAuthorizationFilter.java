package com.gas.shiro;

import cn.hutool.json.JSONUtil;
import com.gas.common.ResponseInfo;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (isAjax((HttpServletRequest) request)) {
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(JSONUtil.toJsonStr(ResponseInfo.error(HttpStatus.UNAUTHORIZED)));
        } else {
            // 非 Ajax 请求，重定向到登录页
            super.onAccessDenied(request, response);
        }
        return false;
    }

    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }
}
