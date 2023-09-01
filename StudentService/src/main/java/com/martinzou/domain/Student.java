package com.martinzou.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value="stu_info")
public class Student {
    @TableId(value = "stu_id")
    private String stuId;

    @TableField(value="stu_name")
    private String stuName;

    @TableField(value="gender")
    private String gender;

    @TableField(value="college")
    private String college;

    @TableField(value="email")
    private String email;

    @TableField(value="telephone")
    private String telephone;

    @TableField(value="password")
    private String password;

}
