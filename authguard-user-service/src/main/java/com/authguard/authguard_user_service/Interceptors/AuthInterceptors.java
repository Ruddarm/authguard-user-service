package com.authguard.authguard_user_service.Interceptors;

import java.util.UUID;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.authguard.authguard_user_service.Context.UserContext;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptors implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        if (uri.startsWith("/auth")) {
            return true;
        }
        log.info("pre handling : {} " + request.getRequestURI());
        String userId = request.getHeader("X-USER-Id");
        if (userId == null) {
            throw new Exception("Invlid user Id");
        }
        UserContext.setUserId(UUID.fromString(userId));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            @Nullable ModelAndView modelAndView) throws Exception {

        UserContext.clear();
        log.info("post handling : {} " + request.getRequestURI());

    }
}
