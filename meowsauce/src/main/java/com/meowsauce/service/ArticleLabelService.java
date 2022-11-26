package com.meowsauce.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.meowsauce.entity.ArticleLabel;

import java.util.List;

public interface ArticleLabelService extends IService<ArticleLabel> {
    List<ArticleLabel> findLIdByAid(int aid);
}
