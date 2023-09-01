package com.martinzou.feign;


import com.martinzou.domain.BookStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "book-service")
public interface BookServiceClient {

    @GetMapping("/bookstatus/{bookId}")
    public Object searchBookStatusInfo(@PathVariable(value = "bookId") String bookId);

    //修改图书信息
    @PutMapping("/bookstatus")
    public String updateBookStatusInfo(@RequestBody BookStatus bookStatus);
}
