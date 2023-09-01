package com.martinzou.controller;


import com.martinzou.domain.StudentUser;
import com.martinzou.service.StudentUserLoginService;
import com.martinzou.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;


@RestController
public class StudentUserController {
    final StudentUserLoginService studentUserLoginService;

    final JWTUtil jwtUtil;

    @Autowired
    public StudentUserController(StudentUserLoginService studentUserLoginService,JWTUtil jwtUtil){
        this.studentUserLoginService=studentUserLoginService;
        this.jwtUtil=jwtUtil;
    }

    @GetMapping("/login/{stuId}/{password}")
    public String studentUserLogin(@PathVariable("stuId") String stuId,@PathVariable("password") String password) throws Exception {
        //如果没有查询则直接显示无相应用户信息
        StudentUser studentUser=studentUserLoginService.getById(stuId);
        if(studentUser==null){
            return "无该学生信息，请联系管理员";
        }
        if(studentUser.getPassword().equals(password)){
            long expirationTime=3600*1000;
            //token一个小时以后过期
            String uuid = UUID.randomUUID().toString().replace("-", "");
            HashMap<String, Object> map = new HashMap<>();
            map.put("stuId", studentUser.getStuId());
            map.put("password", studentUser.getPassword());
            return jwtUtil.createJWT(uuid,"student login",expirationTime,map);
        }
        return "密码错误，请输入正确密码";
    }
}
