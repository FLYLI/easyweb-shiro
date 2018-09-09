package com.faptech.easyweb.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.faptech.easyweb.system.model.User;

public interface UserMapper extends BaseMapper<User> {

    User getByUsername(String username);
}
