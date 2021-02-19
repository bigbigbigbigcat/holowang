package com.holo.springboot.holoclient.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeInterceptor implements HandlerInterceptor {
    Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);

    LocalDateTime beginTime = null;

    /**
     * 实际方法执行前
     * @param request
     * @param response
     * @param handler
     * @return  false则代表拦截，不再继续执行controller中的方法。返回true则代表不拦截。
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        beginTime = LocalDateTime.now();
        return true;
    }

    /**
     * 实际方法执行后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LocalDateTime endNow = LocalDateTime.now();
        //计算两个时间差
        Duration between = Duration.between(beginTime, endNow);
        //获得相关的毫秒数
        logger.info(String.format("当前请求：%s，执行时间：%s",request.getRequestURL(),between.toMillis()));

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
