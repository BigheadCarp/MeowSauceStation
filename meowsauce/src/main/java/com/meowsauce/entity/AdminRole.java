package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@TableName("admin_role")
public class AdminRole {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String nameZh;
    private boolean enabled;
    @TableField(exist = false)
    private List<AdminPermission> perms;
    @TableField(exist = false)
    private List<AdminMenu> menus;
}
