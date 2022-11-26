package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin_role_menu")
public class AdminRoleMenu {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int rid;
    private int mid;
}
