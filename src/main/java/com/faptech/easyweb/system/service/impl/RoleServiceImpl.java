package com.faptech.easyweb.system.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.faptech.easyweb.common.exception.ParameterException;
import com.faptech.easyweb.system.dao.RoleAuthoritiesMapper;
import com.faptech.easyweb.system.dao.RoleMapper;
import com.faptech.easyweb.system.model.Role;
import com.faptech.easyweb.system.model.RoleAuthorities;
import com.faptech.easyweb.system.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private RoleAuthoritiesMapper roleAuthoritiesMapper;

	@Override
	public List<Role> getByUserId(Integer userId) {
		return roleMapper.selectByUserId(userId);
	}

	@Override
	public List<Role> list(boolean showDelete) {
		Wrapper<Role> wrapper = new EntityWrapper<Role>();
		if (!showDelete) {
			wrapper.eq("is_delete", 0);
		}
		return roleMapper.selectList(wrapper.orderBy("create_time", true));
	}

	@Override
	public boolean add(Role role) {
		role.setCreateTime(new Date());
		return roleMapper.insert(role) > 0;
	}

	@Override
	public boolean update(Role role) {
		return roleMapper.updateById(role) > 0;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateState(Integer roleId, int isDelete) {
		if (isDelete != 0 && isDelete != 1) {
			throw new ParameterException("isDelete值需要在[0,1]中");
		}
		Role role = new Role();
		role.setRoleId(roleId);
		role.setIsDelete(isDelete);
		boolean rs = roleMapper.updateById(role) > 0;
		if (rs) {
			// 删除角色的权限
			roleAuthoritiesMapper.delete(new EntityWrapper<RoleAuthorities>().eq("role_id", roleId));
		}
		return rs;
	}

	@Override
	public Role getById(Integer roleId) {
		return roleMapper.selectById(roleId);
	}

	@Override
	public boolean delete(Integer roleId) {
		return roleMapper.deleteById(roleId) > 0;
	}
}
