package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("signature")
public class Signature {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Date signatureDate;
    private String signatureContent;
    private String signatureIcon;
    @TableField(exist = false)
    private String realDate;
}
