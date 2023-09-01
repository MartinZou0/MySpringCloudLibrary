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
@TableName(value = "stu_status")
public class StudentStatus {
    //学生校园卡号
    @TableId(value = "stu_id")
    private String stuId;

    //可借册数(总数)
    @TableField(value = "allow_count")
    private Integer allowCount;


    //在借册数
    @TableField(value = "borrow_count")
    private Integer borrowCount;

    //累计借书
    @TableField(value = "records_count")
    private Integer recordsCount;

    //欠款
    @TableField(value = "debt")
    private Float debt;
}
