package com.martinzou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.martinzou.domain.BookStatus;
import com.martinzou.mapper.BookStatusMapper;
import com.martinzou.service.BookStatusService;
import org.springframework.stereotype.Service;

@Service
public class BookStatusServiceImpl extends ServiceImpl<BookStatusMapper, BookStatus> implements BookStatusService {
}
