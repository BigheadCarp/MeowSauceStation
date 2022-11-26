package com.meowsauce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 主图中信息的展示,文章封面的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleDTO {
    private Integer id;
    private String articleTitle;
    private String type;
    private String readingNum;
    private String articleDate;
    private Integer commentNum;
    private String articleCover;
}
