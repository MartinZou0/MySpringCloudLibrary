package com.martinzou.controller;


import com.martinzou.domain.StudentStatus;
import com.martinzou.service.StuStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class StuStatusController {
    final StuStatusService stuStatusService;

    @Autowired
    public StuStatusController(StuStatusService stuStatusService){
        this.stuStatusService=stuStatusService;
    }

    //添加学生状态信息
    @PostMapping("/stustatus")
    public String addStuInfo(@RequestBody StudentStatus studentStatus){
        if(studentStatus==null||studentStatus.getStuId().isEmpty()){
            return "添加信息为空或缺少学号信息";
        }
        stuStatusService.save(studentStatus);
        return "学生状态信息添加成功";
    }

    //查询学生状态信息
    @GetMapping("/stustatus/{stuId}")
    public Object searchStuStatus(@PathVariable String stuId){
        if(stuId.length() != 10){
            return "请输入正确格式的学号";
        }
        System.out.println(stuStatusService.getById(stuId));
        return stuStatusService.getById(stuId);
    }

    //修改学生状态信息
    @PutMapping("/stustatus")
    public String updateStuInfo(@RequestBody StudentStatus studentStatus){
        stuStatusService.updateById(studentStatus);
        return "学生信息更新成功";
    }

    //删除学生状态消息
    @DeleteMapping("/stustatus/{stuId}")
    public String deleteStuInfo(@PathVariable String stuId){
        if(stuId.length() != 10){
            return "请输入正确格式的学号";
        }
        stuStatusService.removeById(stuId);
        return "删除成功";

    }
}
