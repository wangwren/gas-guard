package com.gas.shiro;

import com.gas.entity.Users;
import com.gas.service.UsersService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UsersService usersService;

    /**
     * 用户登录时，校验用户名密码，会调用此方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户名
        String username = (String) token.getPrincipal();
        // 从数据库中获取用户信息
        Users user = usersService.findByUsername(username);
        if (user == null) {
            throw new UnknownAccountException();
        }
        // 创建并返回一个身份验证信息对象
        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    /**
     * 进行权限校验时，会调用此方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取已认证的用户
        Users user = (Users) principals.getPrimaryPrincipal();
        // 创建一个权限信息对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 从数据库或其他源获取用户角色和权限，然后添加到权限信息对象中
        info.setRoles(usersService.findRoles(user));
        info.setStringPermissions(usersService.findPermissions(user));
        return info;
    }
}
