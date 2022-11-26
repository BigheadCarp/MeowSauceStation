package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("admin_menu")
public class AdminMenu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String path;
    private String name;
    private String nameZh;
    private String iconCls;
    private String component;
    private int parentId;
    @TableField(exist = false)
    List<AdminMenu> children;
}
