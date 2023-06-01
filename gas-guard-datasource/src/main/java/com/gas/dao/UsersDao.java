package com.gas.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gas.dao.mapper.*;
import com.gas.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersDao {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private UserRolesMapper userRolesMapper;
    @Autowired
    private RoleMenuPermissionsMapper roleMenuPermissionsMapper;
    @Autowired
    private MenuPermissionsMapper menuPermissionsMapper;

    public Users findByUsername(String username) {

        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);

        return usersMapper.selectOne(wrapper);
    }

    public List<Roles> findRoles(Users user) {
        QueryWrapper<UserRoles> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",user.getId());

        //查用户对应角色
        List<UserRoles> userRoles = userRolesMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(userRoles)) {
            return null;
        }

        //查角色名称
        List<Integer> rolesId = userRoles.stream().map(UserRoles::getRoleId).collect(Collectors.toList());

        QueryWrapper<Roles> rolesWrapper = new QueryWrapper<>();
        rolesWrapper.in("id", rolesId);

        List<Roles> roles = rolesMapper.selectList(rolesWrapper);
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }

        return roles;
    }

    public List<MenuPermissions> findPermissions(Users user) {
        List<Roles> roles = this.findRoles(user);
        if (CollectionUtils.isEmpty(roles)) {
            return null;
        }

        List<Integer> roleIds = roles.stream().map(Roles::getId).collect(Collectors.toList());

        //通过角色查权限
        QueryWrapper<RoleMenuPermissions> roleMenuPermissionsWrapper = new QueryWrapper<>();
        roleMenuPermissionsWrapper.in("role_id", roleIds);
        List<RoleMenuPermissions> roleMenuPermissions = roleMenuPermissionsMapper.selectList(roleMenuPermissionsWrapper);

        if (CollectionUtils.isEmpty(roleMenuPermissions)) {
            return null;
        }

        //查权限
        List<Integer> menuIds = roleMenuPermissions.stream().map(RoleMenuPermissions::getMenuPermissionId).collect(Collectors.toList());
        QueryWrapper<MenuPermissions> menuPermissionsWrapper = new QueryWrapper<>();
        menuPermissionsWrapper.in("id",menuIds);
        List<MenuPermissions> menuPermissions = menuPermissionsMapper.selectList(menuPermissionsWrapper);

        if (CollectionUtils.isEmpty(menuPermissions)) {
            return null;
        }

        return menuPermissions;
    }
}
