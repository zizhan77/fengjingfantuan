package com.mem.vo.common.util;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: ${PACKAGE_NAME}
 * User: baiyuehui
 * Date: 2019/3/22
 */
@Component
public class myCORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST");
        resp.addHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));
        if (!req.getMethod().equalsIgnoreCase("OPTIONS")) {
            chain.doFilter(request, response);
        }
    }
    @Override
    public void destroy() {

    }
}

