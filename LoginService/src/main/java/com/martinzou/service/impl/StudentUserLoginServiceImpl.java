package com.martinzou.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.martinzou.domain.StudentUser;
import com.martinzou.mapper.StudentUserMapper;
import com.martinzou.service.StudentUserLoginService;
import org.springframework.stereotype.Service;

@Service
public class StudentUserLoginServiceImpl extends ServiceImpl<StudentUserMapper, StudentUser> implements StudentUserLoginService {
}
