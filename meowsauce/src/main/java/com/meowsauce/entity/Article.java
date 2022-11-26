package com.meowsauce.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article")
public class Article {
    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotEmpty(message = "文章标题不能为空")
    private String articleTitle;
    private String articleContentHtml;
    private String articleContentMd;
    private String articleAbstract;
    private String articleCover;
    private Date articleDate;
    private Integer articleView;
    private String articleType;
}
