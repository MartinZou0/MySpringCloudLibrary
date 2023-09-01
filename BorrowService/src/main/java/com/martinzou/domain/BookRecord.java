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
@TableName(value = "book_record")
public class BookRecord {
    @TableId(value = "stu_id")
    private String stuId;

    @TableField(value="book_id")
    private String bookId;

    @TableField(value="borrow_date")
    private Timestamp borrowDate;

    @TableField(value="due")
    private Timestamp due;

    @TableField(value="record_state")
    private Integer recordState;

    @TableField(value="return_date")
    private Timestamp returnDate;

    @TableField(value="breach")
    private Float breach;
}
