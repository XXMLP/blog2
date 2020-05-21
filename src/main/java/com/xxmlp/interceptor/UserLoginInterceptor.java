package com.xxmlp.interceptor;

import com.xxmlp.po.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String sessionId  = request.getSession().getId();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || sessionId != user.getSessionId()) {
            response.sendRedirect("/user");
            return false;
        }
        return true;
    }
}
