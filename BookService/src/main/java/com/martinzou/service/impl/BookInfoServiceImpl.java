package com.martinzou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.martinzou.domain.Book;
import com.martinzou.mapper.BookInfoMapper;
import com.martinzou.service.BookInfoService;
import org.springframework.stereotype.Service;

@Service
public class BookInfoServiceImpl extends ServiceImpl<BookInfoMapper, Book> implements BookInfoService {
}
