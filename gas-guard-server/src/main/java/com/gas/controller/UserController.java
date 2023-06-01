package com.gas.controller;

import com.gas.common.ResponseInfo;
import com.gas.entity.Users;
import com.gas.enums.ErrorCodeEnum;
import com.gas.model.LoginRequest;
import com.gas.model.LoginResponse;
import com.gas.service.UsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseInfo login(@RequestBody @Validated LoginRequest request) {

        Subject subject = SecurityUtils.getSubject();
        try {
            // 进行认证，这会回调 MyShiroRealm 中的 doGetAuthenticationInfo 方法
            subject.login(new UsernamePasswordToken(request.getUsername(), request.getPassword()));


            //登录成功查询用户角色、菜单
            Users user = (Users) subject.getPrincipal();
            LoginResponse response = usersService.findUserInfo(user);

            return ResponseInfo.success(response);
        } catch (AuthenticationException e) {
            return ResponseInfo.error(ErrorCodeEnum.USER_PASSWORD_ERROR);
        } catch (AuthorizationException e) {
            return ResponseInfo.error(ErrorCodeEnum.NOT_PERMIIIONS);
        }
    }

    @PostMapping("/logout")
    public ResponseInfo logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return ResponseInfo.success();
    }
}
