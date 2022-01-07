package com.chen.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@ComponentScan({"com.chen"})
@SpringBootApplication//取消数据源自动配置
@MapperScan("com.chen.ucenterservice.mapper")
public class UcApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcApplication.class, args);
    }
}