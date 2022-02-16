package com.zls.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author zls
 * @date 2022/2/16 13:30:16
 * @description 获取项目路径
 * ServletContextListener 监听servletContext 作用域的创建
 */
public class ContextPathListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String contextPath = servletContext.getContextPath();//项目路径
        servletContext.setAttribute("appPath",contextPath);//将项目路径添加到servletContext作用域
//        System.out.println(contextPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
