package com.gas.model;

import com.gas.dto.MenuDto;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LoginResponse implements Serializable {

    private String userName;

    private List<String> roles;

    private List<MenuDto> menus;
}
