package com.martinzou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.martinzou.domain.BookRecord;
import com.martinzou.mapper.BookRecordMapper;
import com.martinzou.service.BookRecordService;
import org.springframework.stereotype.Service;

@Service
public class BookRecordServiceImpl extends ServiceImpl<BookRecordMapper, BookRecord> implements BookRecordService {
}
