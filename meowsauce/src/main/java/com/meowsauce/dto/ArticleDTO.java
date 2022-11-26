package com.meowsauce.dto;

import com.meowsauce.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 博客归档页面 文章列表项,列出每年每月的文章
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private int year;
    private List<Article> articlesByYear;
    private List<Map<Integer,List<Article>>> month;
}
