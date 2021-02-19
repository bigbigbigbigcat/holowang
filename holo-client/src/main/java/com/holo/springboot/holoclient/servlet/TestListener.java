package com.holo.springboot.holoclient.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TestListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("启动servlet容器~~~");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("销毁servlet容器~~~");
    }
}
