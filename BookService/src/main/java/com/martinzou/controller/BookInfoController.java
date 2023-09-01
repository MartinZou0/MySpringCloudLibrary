package com.martinzou.controller;

import com.martinzou.domain.Book;
import com.martinzou.domain.Hero;
import com.martinzou.service.BookInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookInfoController {
        final BookInfoService bookInfoService;

        @Autowired
        public BookInfoController(BookInfoService bookInfoService){
            this.bookInfoService=bookInfoService;
        }

        @Autowired
        public Hero hero;

        //测试读取配置文件信息
        @GetMapping("/info")
        public String heroInfo() {
            return hero.getName() + ":" + hero.getAge() + ":" + hero.getAddress()+":"+hero.getHobby();
        }
        //添加图书信息
        @PostMapping("/bookinfo")
        public String addBookInfo(@RequestBody Book book){
            if(book==null||book.getBookId().isEmpty()){
                return "添加信息为空或缺少图书ID信息";
            }
            bookInfoService.save(book);
            return "图书信息添加成功";
        }

        //查询图书信息
        @GetMapping("/bookinfo/{bookId}")
        public String searchBookInfo(@PathVariable String bookId){
            if(bookId.isEmpty()){
                return "缺少图书ID信息,无法查询";
            }
            System.out.println(bookInfoService.getById(bookId).getBookId());
            return bookInfoService.getById(bookId).toString();
        }

        //修改图书信息
        @PutMapping("/bookinfo")
        public String updateBookInfo(@RequestBody Book book){
            System.out.println(book);
            bookInfoService.updateById(book);
            return "图书信息更新成功";
        }

        //删除图书消息
        @DeleteMapping("/bookinfo/{bookId}")
        public String deleteBookInfo(@PathVariable String bookId) {
            if (bookId.length() == 6) {
                bookInfoService.removeById(bookId);
                return "删除成功";
            }
            return "请输入正确格式的图书ID";

        }
}
