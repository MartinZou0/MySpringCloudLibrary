package com.martinzou.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "stu_login")
public class StudentUser {
    @TableId(value = "stu_id")
    private String stuId;

    @TableField(value="password")
    private String password;

    @TableField(value="last_Login_Date")
    private Timestamp lastLoginDate;
}
