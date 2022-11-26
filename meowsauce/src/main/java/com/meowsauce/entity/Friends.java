package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("friends")
public class Friends {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String friendName;
    private String friendAddress;
    private String friendIcon;
    private String friendIntroduce;
}
