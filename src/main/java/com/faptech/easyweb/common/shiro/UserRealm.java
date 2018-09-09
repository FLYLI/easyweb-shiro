package com.faptech.easyweb.common.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.faptech.easyweb.common.utils.StringUtil;
import com.faptech.easyweb.system.model.Authorities;
import com.faptech.easyweb.system.model.Role;
import com.faptech.easyweb.system.model.User;
import com.faptech.easyweb.system.service.AuthoritiesService;
import com.faptech.easyweb.system.service.RoleService;
import com.faptech.easyweb.system.service.UserService;

/**
 * Shiro认证和授权 Created by wangfan on 2018-02-22 上午 11:29
 */
public class UserRealm extends AuthorizingRealm {

	@Autowired
	@Lazy
	private UserService userService;

	@Autowired
	@Lazy
	private RoleService roleService;

	@Autowired
	@Lazy
	private AuthoritiesService authoritiesService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		// 角色
		List<Role> userRoles = roleService.getByUserId(user.getUserId());
		Set<String> roles = new HashSet<>();
		for (Role userRole : userRoles) {
			roles.add(String.valueOf(userRole.getRoleId()));
		}

		authorizationInfo.setRoles(roles);

		// 权限
		List<Authorities> authorities = authoritiesService.listByUserId(user.getUserId());
		Set<String> permissions = new HashSet<>();
		for (Authorities authority : authorities) {
			String authorityFlag = authority.getAuthority();
			if (StringUtil.isNotBlank(authorityFlag)) {
				permissions.add(authorityFlag);
			}
		}

		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		String username = (String) authenticationToken.getPrincipal();
		User user = userService.getByUsername(username);

		if (null == user) {
			throw new UnknownAccountException(); // 账号不存在
		}

		if (user.getState() != 0) {
			throw new LockedAccountException(); // 账号被锁定
		}

		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
				ByteSource.Util.bytes(user.getUsername()), getName());
		return authenticationInfo;
	}
}
