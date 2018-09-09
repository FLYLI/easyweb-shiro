package com.faptech.easyweb.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.faptech.easyweb.system.model.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> selectByUserIds(@Param("userIds") List<Integer> userIds);

    int insertBatch(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);
}
