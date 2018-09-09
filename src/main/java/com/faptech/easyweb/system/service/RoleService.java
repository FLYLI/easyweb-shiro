package com.faptech.easyweb.system.service;

import java.util.List;

import com.faptech.easyweb.system.model.Role;

public interface RoleService {

    List<Role> getByUserId(Integer userId);

    List<Role> list(boolean showDelete);

    Role getById(Integer roleId);

    boolean add(Role role);

    boolean update(Role role);

    boolean updateState(Integer roleId, int isDelete);  // 逻辑删除

    boolean delete(Integer roleId);  // 物理删除

}
