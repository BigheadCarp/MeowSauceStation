package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("pigeonhole")
@AllArgsConstructor
@NoArgsConstructor
public class Pigeonhole {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String pigeonholeDate;
    private String pigeonholeContent;
}
