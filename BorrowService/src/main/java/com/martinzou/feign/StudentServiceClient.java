package com.martinzou.feign;


import com.martinzou.domain.StudentStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "student-service")
public interface StudentServiceClient {

    @GetMapping("/stustatus/{stuId}")
    public Object searchStuStatus(@PathVariable("stuId") String stuId);

    //修改学生状态信息
    @PutMapping("/stustatus")
    public String updateStuInfo(@RequestBody StudentStatus studentStatus);
}
