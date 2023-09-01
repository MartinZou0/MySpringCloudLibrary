package com.martinzou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//开启Feign的客户端
@EnableFeignClients
@MapperScan("com.martinzou.mapper")
public class BorrowServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BorrowServiceApplication.class, args);
    }

}
