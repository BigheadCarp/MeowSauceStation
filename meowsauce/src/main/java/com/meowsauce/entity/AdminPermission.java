package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.lang.reflect.Type;

@Data
@TableName("admin_permission")
public class AdminPermission {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String des;

    private String url;
}
