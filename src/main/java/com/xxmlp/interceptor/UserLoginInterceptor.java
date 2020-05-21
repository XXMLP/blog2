package com.xxmlp.interceptor;

import com.xxmlp.po.Session;
import com.xxmlp.po.User;
import com.xxmlp.service.SessionService;
import com.xxmlp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserLoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String sessionId  = request.getSession().getId();
        User user = (User) request.getSession().getAttribute("user");
        Session session = sessionService.getSessionById(user.getId(),sessionId);
        if (user == null ) {
            response.sendRedirect("/user");
            return false;
        }if (session ==null ) {
            request.getSession().removeAttribute("user");
            response.sendRedirect("/user");
            return false;
        }
        return true;
    }
}
