package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("admin_user_role")
public class AdminUserRole {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int uid;
    private int rid;
}
