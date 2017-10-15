package com.misnearzhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * Created by zhanglong on 2017/7/14.
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.misnearzhang")
public class AppIndex {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(
                AppIndex.class);
        application.addListeners(
                new ApplicationPidFileWriter("app.pid"));
        application.run(args);
    }
}
