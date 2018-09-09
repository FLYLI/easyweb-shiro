package com.faptech.easyweb.system.service;

import java.util.List;

import com.faptech.easyweb.system.model.Authorities;

public interface AuthoritiesService {

    List<Authorities> listByUserId(Integer userId);

    List<Authorities> list();

    List<Authorities> listMenu();

    List<Authorities> listByRoleIds(List<Integer> roleId);

    List<Authorities> listByRoleId(Integer roleId);

    boolean add(Authorities authorities);

    boolean update(Authorities authorities);

    boolean delete(Integer authorityId);

    boolean addRoleAuth(Integer roleId, Integer authId);

    boolean deleteRoleAuth(Integer roleId, Integer authId);

    boolean updateRoleAuth(Integer roleId, List<Integer> authIds);
}
