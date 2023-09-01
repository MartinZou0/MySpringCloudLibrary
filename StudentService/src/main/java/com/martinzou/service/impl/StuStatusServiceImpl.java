package com.martinzou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.martinzou.domain.StudentStatus;
import com.martinzou.mapper.StuInfoMapper;
import com.martinzou.mapper.StuStatusMapper;
import com.martinzou.service.StuStatusService;
import org.springframework.stereotype.Service;

@Service
public class StuStatusServiceImpl extends ServiceImpl<StuStatusMapper, StudentStatus> implements StuStatusService {
}
