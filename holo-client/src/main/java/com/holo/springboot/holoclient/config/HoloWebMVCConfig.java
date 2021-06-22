package com.holo.springboot.holoclient.config;

import com.holo.springboot.holoclient.interceptors.TimeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class HoloWebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new TimeInterceptor());//这里是添加拦截器
        interceptorRegistration.addPathPatterns("/**");//拦截映射规则
        interceptorRegistration.excludePathPatterns("/qryOrderInfo/**");//排除掉某些请求
    }

    /**
     * 设置全局跨域
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //前后端分离的场景，就可以通过这种方式进行全局跨域处理，或者通过@CrossOrigin来指定某个controller方法为跨域
        registry.addMapping("/qryOrderInfo/**")//表示哪些接口是允许跨域
                .allowedOrigins("http://www.baidu.com")//代表允许的跨域来源
                .allowedMethods("POST", "GET");
    }
}
