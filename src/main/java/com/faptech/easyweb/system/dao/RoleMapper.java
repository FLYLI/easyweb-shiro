package com.faptech.easyweb.system.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.faptech.easyweb.system.model.Role;

public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectByUserId(Integer userId);
}
