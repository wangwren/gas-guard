package com.gas.service.impl;

import com.gas.dao.UsersDao;
import com.gas.dto.MenuDto;
import com.gas.entity.MenuPermissions;
import com.gas.entity.Roles;
import com.gas.entity.Users;
import com.gas.model.LoginResponse;
import com.gas.service.UsersService;
import com.gas.utils.TreeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;

    @Override
    public Users findByUsername(String username) {
        return usersDao.findByUsername(username);
    }

    /**
     * 查询用户角色
     */
    @Override
    public Set<String> findRoles(Users user) {

        List<Roles> roles = usersDao.findRoles(user);
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }

        Set<String> rolesName = roles.stream().map(Roles::getRoleName).collect(Collectors.toSet());
        return rolesName;
    }

    /**
     * 查询用户对应权限列表
     */
    @Override
    public Set<String> findPermissions(Users user) {

        List<MenuPermissions> permissions = usersDao.findPermissions(user);
        if (CollectionUtils.isEmpty(permissions)) {
            return null;
        }

        Set<String> set = permissions.stream().map(MenuPermissions::getPermission).collect(Collectors.toSet());
        return set;
    }

    @Override
    public LoginResponse findUserInfo(Users user) {

        LoginResponse response = new LoginResponse();
        //设置用户名
        response.setUserName(user.getUsername());

        //设置角色
        Set<String> roles = this.findRoles(user);
        if (!CollectionUtils.isEmpty(roles)) {
            response.setRoles(new ArrayList<>(roles));
        }

        //设置菜单
        List<MenuPermissions> permissions = usersDao.findPermissions(user);
        List<MenuDto> menuDtos = permissions.stream().map(permission -> {
            MenuDto menuDto = new MenuDto();
            BeanUtils.copyProperties(permission, menuDto);
            return menuDto;
        }).collect(Collectors.toList());

        response.setMenus(TreeUtils.build(menuDtos));


        return response;
    }
}
