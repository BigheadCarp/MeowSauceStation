package com.meowsauce.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.dto.ArticleDTO;
import com.meowsauce.dto.TotalDTO;
import com.meowsauce.entity.Article;

import java.util.Collection;
import java.util.List;

public interface ArticleService extends IService<Article> {
    void addOrUpdate(Article article);

    Page<Article> list(int i, int size);

    Article findById(int id);

    void delete(int id);

    List<Article> getAllArticle();

    Page<Article> listByArticleTitle(int page, int size, String articleTitle);

    Page<Article> listByArticleType(int page, int size, String articleType);

    int getArticleTotalByType(String articleType);

    int getArticleTotalByArticleTitle(String articleTitle);

    List<ArticleDTO> getArticleByTime();

    List<Article> getArticleToHavingLooking();


    void ReadingNumAdd(int id);
}
