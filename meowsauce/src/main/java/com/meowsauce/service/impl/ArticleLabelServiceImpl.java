package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.ArticleLabel;
import com.meowsauce.mapper.ArticleLabelMapper;
import com.meowsauce.service.ArticleLabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleLabelServiceImpl extends ServiceImpl<ArticleLabelMapper, ArticleLabel> implements ArticleLabelService {
    @Autowired
    private ArticleLabelMapper articleLabelMapper;
    //根据文章Id查询标签
    @Override
    public List<ArticleLabel> findLIdByAid(int aid) {
        QueryWrapper<ArticleLabel> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("aid",aid);
        List<ArticleLabel> labels = articleLabelMapper.selectList(queryWrapper);
        return labels;
    }
}
