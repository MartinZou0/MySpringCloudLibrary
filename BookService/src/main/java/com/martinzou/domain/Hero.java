package com.martinzou.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@RefreshScope //给这个类添加一个刷新的作用域
public class Hero {
    @Value("${hero.name}")
    private String name;

    @Value("${hero.age}")
    private Integer age;

    @Value("${hero.address}")
    private String address;

    //读两个配置文件，前三个属性由配置文件book-service.yaml提供
    //hobby属性book-service-2中的配置文件进行提供
    @Value("${hero.hobby}")
    private String hobby;
}
