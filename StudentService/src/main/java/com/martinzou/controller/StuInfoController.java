package com.martinzou.controller;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.martinzou.domain.Student;
import com.martinzou.service.StuInfoService;
import com.martinzou.service.impl.StuInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/*
*
* */
@RestController
public class StuInfoController {
    final StuInfoService stuInfoService;

    @Autowired
    public StuInfoController(StuInfoService stuInfoService){
        this.stuInfoService=stuInfoService;
    }

    //添加学生信息
    @PostMapping("/stuinfo")
    public String addStuInfo(@RequestBody Student student){
        if(student==null||student.getStuId().isEmpty()){
            return "添加信息为空或缺少学号信息";
        }
        stuInfoService.save(student);
        return "学生信息添加成功";
    }

    //查询学生信息
    @GetMapping("/stuinfo/{stuId}")
    public String searchStuInfo(@PathVariable String stuId){
        if(stuId.isEmpty()){
            return "缺少学号信息,无法查询";
        }
        System.out.println(stuInfoService.getById(stuId).getStuName());
        return stuInfoService.getById(stuId).toString();
    }

    //修改学生信息
    @PutMapping("/stuinfo")
    public String updateStuInfo(@RequestBody Student student){
        stuInfoService.updateById(student);
        return "学生信息更新成功";
    }

    //删除学生消息
    @DeleteMapping("/stuinfo/{stuId}")
    public String deleteStuInfo(@PathVariable String stuId){
        if(stuId.length() != 10){
            return "请输入正确格式的学号";
        }
        stuInfoService.removeById(stuId);
        return "删除成功";

    }


}
