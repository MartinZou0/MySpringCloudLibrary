package com.martinzou.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.martinzou.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/*
*
* 前端约定好一般放在请求头里面一般叫Authorization
* token校验
* 1.拿到请求url
* 2.判断放行
* 3.拿到请求头
* 4.拿到token
* 5.校验
* 6.放行/拦截
* */
@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {
    @Resource
    private JWTUtil jwtUtil;
    //指定好放行的路径,登陆是不需要验证的
    public static final List<String>ALLOW_URL= Arrays.asList("/login-service/login");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request=exchange.getRequest();
        ServerHttpResponse response=exchange.getResponse();
        String path=request.getURI().getPath();
        System.out.println(path);
        if(path.contains(ALLOW_URL.get(0))){
            return chain.filter(exchange);
        }
        //检查
        HttpHeaders headers =request.getHeaders();
        List<String> authorization=headers.get("Authorization");
        HashMap<String,Object> map=new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes;
        DataBuffer wrap;
        if(!authorization.isEmpty()){
            String token=authorization.get(0);
            if(StringUtils.hasText(token)){
                try{
                    Claims claims=jwtUtil.parseJWT(token);
                    return chain.filter(exchange);
                }catch (SignatureException e){
                    map.put("code",401);
                    map.put("message","身份信息认证失败请重新登录");
                }catch (ExpiredJwtException e){
                    map.put("code",411);
                    map.put("message","身份验证已经过期请重新登录");
                }
                try {
                    bytes=objectMapper.writeValueAsBytes(map);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                wrap=response.bufferFactory().wrap(bytes);
                return response.writeWith(Mono.just(wrap));
            }
        }
        map.put("code",9527);
        map.put("message","连身份认证信息都没有!还想用服务？");
        try {
            bytes=objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        wrap=response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
