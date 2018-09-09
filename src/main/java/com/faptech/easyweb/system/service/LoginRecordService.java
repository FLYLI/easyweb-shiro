package com.faptech.easyweb.system.service;

import com.faptech.easyweb.common.PageResult;
import com.faptech.easyweb.system.model.LoginRecord;

public interface LoginRecordService {

    boolean add(LoginRecord loginRecord);

    PageResult<LoginRecord> list(int pageNum, int pageSize, String startDate, String endDate, String account);
}
