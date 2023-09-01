package com.martinzou.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;


//为了
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {

    /*
    * 这个就是过滤的方法
    * 责任链模式
    * */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        //针对请求的过滤 拿到请求 header url 参数。。。
//        ServerHttpRequest request=exchange.getRequest();
//        //ServerHttpRequest :这个是webflux里面的
//        //HttpServletRequest这个是web里面的
//        String path=request.getURI().getPath();
//        System.out.println(path);
//        HttpHeaders headers=request.getHeaders();
//        System.out.println(headers);
//        String methodName=request.getMethod().name();
//        System.out.println(methodName);
//        //主机名称
//        System.out.println(request.getRemoteAddress().getHostName());
//        //显示0:0:0:0:0:0:0:1
//        //响应相关的数据
//        ServerHttpResponse response=exchange.getResponse();
//        //微服务肯定是前后端分离，一般通过json
//        //在响应头里面设置编码
//        response.getHeaders().set("content-type","application/json;charset=utf-8");
//        //组装业务返回值
//        HashMap<String,Object>map=new HashMap<>();
//        map.put("code", HttpStatus.UNAUTHORIZED.value());
//        map.put("message","你未进行授权");
//        ObjectMapper objectMapper=new ObjectMapper();
//        byte[] bytes = new byte[0];
//        try {
//            //将map转成一个字节
//            bytes = objectMapper.writeValueAsBytes(map);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        //通过buffer工厂将字节数组包装成数据包
//        DataBuffer wrap = response.bufferFactory().wrap(bytes);
//        return response.writeWith(Mono.just(wrap));
        //请求放行，过滤器遵从责任链模式
        return chain.filter(exchange);
    }

    //指定顺序的方法，越小越先执行
    @Override
    public int getOrder() {
        return 1;
    }
}
