package com.gas.dto;


import com.gas.utils.TreeNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuDto extends TreeNode<MenuDto> implements Serializable {

    private Integer id;

    private Integer parentId;

    private String name;

    //前端渲染路径，配置在数据库中
    private String path;

    private String icon;
}
