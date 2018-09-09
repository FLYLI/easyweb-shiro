package com.faptech.easyweb.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.faptech.easyweb.common.PageResult;
import com.faptech.easyweb.system.dao.LoginRecordMapper;
import com.faptech.easyweb.system.model.LoginRecord;
import com.faptech.easyweb.system.service.LoginRecordService;

@Service
public class LoginRecordServiceImpl implements LoginRecordService {
    @Autowired
    private LoginRecordMapper loginRecordMapper;

    @Override
    public boolean add(LoginRecord loginRecord) {
        loginRecord.setCreateTime(new Date());
        return loginRecordMapper.insert(loginRecord) > 0;
    }

    @Override
    public PageResult<LoginRecord> list(int pageNum, int pageSize, String startDate, String endDate, String account) {
        Page<LoginRecord> page = new Page<>(pageNum, pageSize);
        List<LoginRecord> records = loginRecordMapper.listFull(page, startDate, endDate, account);
        return new PageResult<>(page.getTotal(), records);
    }
}