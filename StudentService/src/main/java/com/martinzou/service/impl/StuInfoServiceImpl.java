package com.martinzou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.martinzou.domain.Student;
import com.martinzou.mapper.StuInfoMapper;
import com.martinzou.service.StuInfoService;
import org.springframework.stereotype.Service;

@Service
public class StuInfoServiceImpl extends ServiceImpl<StuInfoMapper, Student> implements StuInfoService {
}
