package com.meowsauce.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.meowsauce.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 主要用于文章评论,关于我页面的评论
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Integer cId;

    private Integer articleId;

    private String commentDate;

    private String commentContent;

    private Integer parentCommentId;

    private String nickName;

    private String cover;

    private String email;

    private String website;

    @TableField(exist = false)
    List<CommentDTO> children;

}
