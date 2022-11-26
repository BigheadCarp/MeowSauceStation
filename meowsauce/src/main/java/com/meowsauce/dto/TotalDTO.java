package com.meowsauce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 归档页面总览信息,卡片组件中的信息,博客中文章,评论,标签数量
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalDTO {
    private int articleTotal;
    private int labelTotal;
    private int commentTotal;
}
