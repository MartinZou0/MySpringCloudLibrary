package com.martinzou.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/*
* 网关里面 拦截器
* ip拦截
* 请求都有一个源头
* 请求--->网关---->service
* 黑名单：black_list
* 白名单：
* 根据数量
* 像具体的业务一般用黑名单
* 像数据库一般用用白名单
* */
@Component
public class IPCheckFilter implements GlobalFilter, Ordered {

    /*
    * 网关的并发比较高，不要在网关里面直接操作mysql
    * 后台的系统可以去查询数据库 并发量不大
    * 如果并发量大 可以去查redis或者在内存中
    *
    * */
    public static final List<String>BLACK_LIST= Arrays.asList("127.0.0.1");
    /*
    * 对IP进行拦截
    * 1.拿到IP
    * 2.校验IP是否符合规范
    * 3.放行、拦截
    * */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("现在是IPFilter");
        ServerHttpRequest request = exchange.getRequest();
        String ip=request.getHeaders().getHost().getHostString();
        //查询数据库,看这个IP是否存在黑名单里面 mysql数据库的并发并不大
        //只要能存储数据的地方都能叫数据库redis mysql
        if(!BLACK_LIST.contains(ip)){
            System.out.println(!BLACK_LIST.contains(ip));
            return chain.filter(exchange);
        }
        ServerHttpResponse response= exchange.getResponse();
        response.getHeaders().set("content-type","application/json;charset=utf-8");
        HashMap<String,Object>map=new HashMap<>();
        //约定好600是黑名单访问未授权
        map.put("code",438);
        map.put("message","你是黑名单");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes=new byte[0];
        try {
            bytes=objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer wrap=response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return -5;
    }
}
