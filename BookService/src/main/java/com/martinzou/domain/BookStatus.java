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
@TableName(value="book_status")
public class BookStatus {
    @TableId(value = "book_id")
    private String bookId;

    //1，表示为可借阅。0表示当前此书不可借阅
    @TableField(value = "book_state")
    private Integer bookState;

    @TableField(value = "record_count")
    private Integer recordCount;


}
