package com.martinzou.controller;


import com.martinzou.domain.BookStatus;
import com.martinzou.service.BookStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookStatusController {
        final BookStatusService bookStatusService;

        @Autowired
        public BookStatusController(BookStatusService bookStatusService){
            this.bookStatusService=bookStatusService;
        }

        //添加图书信息
        @PostMapping("/bookstatus")
        public String addBookStatusInfo(@RequestBody BookStatus bookStatus){
            if(bookStatus==null||bookStatus.getBookId().isEmpty()){
                return "添加信息为空或缺少图书ID信息";
            }
            bookStatusService.save(bookStatus);
            return "图书状态信息添加成功";
        }

        //查询图书信息
        @GetMapping("/bookstatus/{bookId}")
        public Object searchBookStatusInfo(@PathVariable String bookId){
            if(bookId.isEmpty()){
                return "缺少图书ID信息,无法查询";
            }
            return bookStatusService.getById(bookId);
        }

        //修改图书信息
        @PutMapping("/bookstatus")
        public String updateBookStatusInfo(@RequestBody BookStatus bookStatus){
            System.out.println(bookStatus);
            bookStatusService.updateById(bookStatus);
            return "图书状态信息更新成功";
        }

        //删除图书消息
        @DeleteMapping("/bookstatus/{bookId}")
        public String deleteBookStatusInfo(@PathVariable String bookId){
            if(bookId.length()==6){
                bookStatusService.removeById(bookId);
                return "删除成功";
            }
            return "请输入正确格式的图书ID";

        }
}
