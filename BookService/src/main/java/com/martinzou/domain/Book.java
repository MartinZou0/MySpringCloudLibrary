package com.martinzou.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "book_info")
public class Book {
    @TableId(value = "book_id")
    private String bookId;

    //@TableField(value="book_name")
    private String bookName;

    @TableField(value="author")
    private String author;

    @TableField(value="publisher")
    private String publisher;

    @TableField(value="pubdate")
    private Date pubdate;

    @TableField(value = "isbn")
    private String isbn;

    @TableField(value="price")
    private Float price;

    @TableField(value="type")
    private String type;

    @TableField(value="location")
    private String location;
}
