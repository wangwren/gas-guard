package com.gas.dto;

import com.gas.utils.TreeNode;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuDto extends TreeNode<MenuDto> implements Serializable {

    private Integer id;

    private Integer parentId;

    private String name;
}
