package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.entity.Article;
import com.meowsauce.entity.ArticleLabel;
import com.meowsauce.entity.Labels;
import com.meowsauce.mapper.LabelsMapper;
import com.meowsauce.redis.RedisService;
import com.meowsauce.service.ArticleLabelService;
import com.meowsauce.service.ArticleService;
import com.meowsauce.service.LabelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LabelsServiceImpl extends ServiceImpl<LabelsMapper, Labels> implements LabelsService {
    @Autowired
    private LabelsMapper labelsMapper;
    @Autowired
    private ArticleLabelService articleLabelService;
    @Autowired
    private RedisService redisService;
    //查询所有标签
    @Override
    public List<Labels> getAllLabel() {
        return labelsMapper.selectList(null);
    }
    //根据文章Id查询所有标签并以数组方式返回 用于后台文章编辑功能中标签的展示
    @Override
    public String[] getAllLabelByAidToArray(int aid) {
            List<Integer> lIds = articleLabelService.findLIdByAid(aid).stream().map(ArticleLabel::getLid).collect(Collectors.toList());
            List<String> labels = labelsMapper.selectBatchIds(lIds).stream().map(Labels::getLabelName).collect(Collectors.toList());
            String[] result = new String[labels.size()];
            int index = 0;
            for (String label : labels){
                result[index++] = label;
            }
            return result;
    }
    //根据文章Id保存标签信息
    @Override
    public void saveLabelsByAId(int id, String[] label) {
        for (String s : label) {
            Labels labels = new Labels();
            ArticleLabel articleLabel = new ArticleLabel();
            //查询是否存在该标签
            QueryWrapper<Labels> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("label_name",s);
            Labels selectOne = labelsMapper.selectOne(queryWrapper);
            if (selectOne != null){
                articleLabelService.removeById(id);
                articleLabel.setAid(id);
                articleLabel.setLid(selectOne.getId());
                articleLabelService.save(articleLabel);
            }else {
                articleLabelService.removeById(id);
                labels.setLabelName(s);
                labels.setLabelDate(new Date());
                labelsMapper.insert(labels);
                articleLabel.setAid(id);
                articleLabel.setLid(labels.getId());
                articleLabelService.save(articleLabel);
            }
        }
    }
}
