package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.xml.internal.ws.developer.Serialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comments")
public class Comment implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer articleId;
    private Date commentDate;
    private String commentContent;
    private Integer parentCommentId;

    @TableField(exist = false)
    List<Comment> children;
}
