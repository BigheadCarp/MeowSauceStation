package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("visitor")
public class Visitor {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String nickName;
    private String cover;
    private String email;
    private String website;
}
