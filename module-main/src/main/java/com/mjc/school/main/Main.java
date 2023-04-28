package com.mjc.school.main;


import com.mjc.school.config.ProjectConfig;
import com.mjc.school.controller.menumanager.MenuManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try(var context = new AnnotationConfigApplicationContext(ProjectConfig.class)){
            var menuManager = context.getBean(MenuManager.class);
            menuManager.run();
        }
    }
}