package com.martinzou.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.martinzou.domain.BookRecord;
import com.martinzou.domain.BookStatus;
import com.martinzou.domain.StudentStatus;
import com.martinzou.feign.BookServiceClient;
import com.martinzou.feign.StudentServiceClient;
import com.martinzou.service.BookRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
public class BookRecordController{
    final BookRecordService bookRecordService;

    private BookServiceClient bookServiceClient;

    private StudentServiceClient studentServiceClient;

    @Autowired
    public BookRecordController(BookRecordService bookRecordService,BookServiceClient bookServiceClient,StudentServiceClient studentServiceClient){
        this.bookRecordService=bookRecordService;
        this.bookServiceClient=bookServiceClient;
        this.studentServiceClient=studentServiceClient;
    }

    //OpenFeign调用，调用成功，新增借阅记录
    @PostMapping(value="/bookrecord/{stuId}/{bookId}")
    public String addBookRecord(@PathVariable(value ="stuId") String stuId,@PathVariable(value="bookId") String bookId){
        if(stuId.isEmpty()||bookId.isEmpty()){
            return "缺乏必要的学生ID或图书ID";
        }
        //将object对象转化为实体类对象
        StudentStatus studentStatus=JSON.parseObject(JSON.toJSONString(studentServiceClient.searchStuStatus(stuId)),StudentStatus.class);
        BookStatus bookStatus=JSON.parseObject(JSON.toJSONString(bookServiceClient.searchBookStatusInfo(bookId)),BookStatus.class);
        if(studentStatus.getBorrowCount()>=studentStatus.getAllowCount()){
            return "所借阅图书数目已经达到上限请归回图书后再借阅";
        }
        if(studentStatus.getDebt()!=0){
            return "该账户有欠款，请将欠款缴清再进行借阅";
        }
        if(bookStatus.getBookState()==0){
            return "当前书本不可借阅";
        }
        BookRecord bookRecord=new BookRecord();
        bookRecord.setStuId(stuId);
        bookRecord.setBookId(bookId);
        Timestamp timestamp=new Timestamp(new Date().getTime());
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        System.out.println(ft.format(timestamp));
        bookRecord.setBorrowDate(Timestamp.valueOf(ft.format(timestamp)));
        Calendar c=Calendar.getInstance();
        c.setTime(timestamp);
        c.add(Calendar.DATE,14);
        bookRecord.setDue(Timestamp.valueOf(ft.format(new Timestamp(c.getTimeInMillis()))));
        bookRecord.setRecordState(1);
        bookRecord.setBreach(0.0f);
        bookRecordService.save(bookRecord);
        //学生状态和图书状态进行更新
        studentStatus.setBorrowCount(studentStatus.getBorrowCount()+1);
        studentStatus.setRecordsCount(studentStatus.getRecordsCount()+1);
        //该书本已被借出不可借
        bookStatus.setBookState(0);
        bookStatus.setRecordCount(bookStatus.getRecordCount()+1);
        //更新状态
        studentServiceClient.updateStuInfo(studentStatus);
        bookServiceClient.updateBookStatusInfo(bookStatus);
        return "借书成功";
    }

    //查询借阅记录
    @GetMapping(value = "/bookrecord/{stuId}/{bookId}")
    public Object searchBookRecord(@PathVariable String stuId,@PathVariable String bookId){
        if(stuId.isEmpty()||bookId.isEmpty()){
            return "缺乏必要的学生ID或图书ID";
        }
        QueryWrapper<BookRecord> wrapper=new QueryWrapper<>();
        wrapper.eq("stu_id",stuId).eq("book_id",bookId);
        return bookRecordService.getOne(wrapper);
    }


    //修改借阅记录
    @PutMapping(value = "/bookrecord")
    public String updateBookRecord(@RequestBody BookRecord bookRecord){
        if(bookRecord.getStuId().isEmpty()||bookRecord.getBookId().isEmpty()){
            return "缺乏必要的学生ID或图书ID";
        }
        bookRecordService.save(bookRecord);
        return "记录更新成功";
    }

    //还书操作
    @PutMapping(value = "/bookrecord/{stuId}/{bookId}")
    public String returnBookRecord(@PathVariable String stuId,@PathVariable String bookId){
        if(stuId.isEmpty()||bookId.isEmpty()){
            return "缺乏必要的学生ID或图书ID";
        }
        BookRecord bookRecord=JSON.parseObject(JSON.toJSONString(this.searchBookRecord(stuId,bookId)),BookRecord.class);
        StudentStatus studentStatus=JSON.parseObject(JSON.toJSONString(studentServiceClient.searchStuStatus(stuId)),StudentStatus.class);
        BookStatus bookStatus=JSON.parseObject(JSON.toJSONString(bookServiceClient.searchBookStatusInfo(bookId)),BookStatus.class);
        bookRecord.setRecordState(2);
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        bookRecord.setReturnDate(Timestamp.valueOf(ft.format(timestamp)));
        if(bookRecord.getDue().compareTo(timestamp)==-1){
            bookRecord.setBreach(10f);
        }
        bookRecordService.updateById(bookRecord);
        //更新学生状态以及书本状态
        studentStatus.setBorrowCount(studentStatus.getBorrowCount()-1);
        bookStatus.setBookState(1);
        studentServiceClient.updateStuInfo(studentStatus);
        bookServiceClient.updateBookStatusInfo(bookStatus);
        return "还书成功";
    }


    @DeleteMapping("/bookrecord/{stuId}/{bookId}")
    public String deleteBookRecord(@PathVariable String stuId,@PathVariable String bookId){
        if(stuId.isEmpty()||bookId.isEmpty()){
            return "缺乏必要的学生ID或图书ID";
        }
        if(searchBookRecord(stuId,bookId) instanceof String){
            return "没有找到相关记录，请核对学号以及图书ID";
        }
        QueryWrapper<BookRecord> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("stu_id",stuId).eq("book_id",bookId);
        bookRecordService.remove(queryWrapper);
        return "删除记录成功";
    }


}
