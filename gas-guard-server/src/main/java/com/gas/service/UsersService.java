package com.gas.service;

import com.gas.entity.Users;
import com.gas.model.LoginResponse;

import java.util.Set;

public interface UsersService {
    Users findByUsername(String username);

    /**
     * 角色名称
     */
    Set<String> findRoles(Users user);

    /**
     * 菜单权限列表，不包含菜单名
     */
    Set<String> findPermissions(Users user);

    /**
     * 查询用户角色、菜单
     */
    LoginResponse findUserInfo(Users user);
}
