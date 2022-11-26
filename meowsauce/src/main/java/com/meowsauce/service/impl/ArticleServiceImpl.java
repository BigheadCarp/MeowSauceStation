package com.meowsauce.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.meowsauce.dto.ArticleDTO;
import com.meowsauce.dto.TotalDTO;
import com.meowsauce.entity.Article;
import com.meowsauce.mapper.ArticleMapper;
import com.meowsauce.redis.RedisService;
import com.meowsauce.service.ArticleService;
import com.meowsauce.service.CommentService;
import com.meowsauce.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisService redisService;
    //根据文章Id判断是更新文章还是添加文章
    @Transactional
    @Override
    public void addOrUpdate(Article article) {
        Integer id = article.getId();
        if (id != null){
            updateById(article);
        }else {
            save(article);
        }
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.deleteArticleCache(id);
    }
    //查询文章并进行分页
    @Override
    public Page<Article> list(int page, int size) {
        String key = "articlePage" + page;
        Object articlePageCache = redisService.get(key);
        if (articlePageCache == null){
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.orderByDesc(Article::getId);
            Page<Article> articlePage = new Page<>(page,size);
            Page<Article> resultPage = articleMapper.selectPage(articlePage, queryWrapper);
            redisService.set(key,resultPage);
            return resultPage;
        }else {
            Page<Article> articlePage = new Page<>();
            articlePage = (Page<Article>) articlePageCache;
            return articlePage;
        }
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//            queryWrapper.orderByDesc(Article::getId);
//            Page<Article> articlePage = new Page<>(page,size);
//            Page<Article> resultPage = articleMapper.selectPage(articlePage, queryWrapper);
//        System.out.println("总页数: "+resultPage.getPages());
//        System.out.println("总记录数: " + resultPage.getTotal());
//        return resultPage;
    }
    //根据文章ID查询文章
    @Override
    public Article findById(int id) {
//        Article article = articleMapper.selectById(id);
        Article article;
        String key = "article:" + id;
        Object articleCache = redisService.get(key);
        if (articleCache == null){
            article = articleMapper.selectById(id);
            redisService.set(key,article);
        }else {
            article = (Article) articleCache;
        }
        return article;
    }
    //根据文章Id删除文章
    @Transactional
    @Override
    public void delete(int id) {
        removeById(id);
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.deleteArticleCache(id);
    }
    //查询所有文章
    @Override
    public List<Article> getAllArticle() {
        List<Article> articles;
        String key = "articleList";
        Object articleCache = redisService.get(key);
        if (articleCache == null){
            articles = articleMapper.selectList(null);
            redisService.set(key,articles);
        }else {
            articles = (List<Article>) articleCache;
        }
//        List<Article> collect = articleMapper.selectList(null);
        return articles;
    }
//    搜索文章
    @Override
    public Page<Article> listByArticleTitle(int page, int size, String articleTitle) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.like("article_title",articleTitle);
        Page<Article> articlePage = new Page<>(page, size);
        Page<Article> resultPage = articleMapper.selectPage(articlePage, queryWrapper);
        return resultPage;
    }
    //分类文章
    @Override
    public Page<Article> listByArticleType(int page, int size, String articleType) {
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_type",articleType);
        Page<Article> articlePage = new Page<>(page,size);
        Page<Article> resultPage = articleMapper.selectPage(articlePage,queryWrapper);
        return resultPage;
    }
    //根据分类查询文章总数
    @Override
    public int getArticleTotalByType(String articleType) {
        String key = "getArticleTotalByType";
        Object redisCache = redisService.get(key);
        if (redisCache == null){
            QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("article_type",articleType);
            int size = articleMapper.selectList(queryWrapper).size();
            redisService.set(key,size);
            return size;
        }else {
            return (int) redisCache;
        }
    }
    //根据文章标题搜索文章总数
    @Override
    public int getArticleTotalByArticleTitle(String articleTitle) {
            QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("article_title",articleTitle);
            int size = articleMapper.selectList(queryWrapper).size();
            return size;
    }
    //归档中根据时间查询每年每月的文章
    @Override
    public List<ArticleDTO> getArticleByTime() {
        List<ArticleDTO> articleDTOS;
        String key = "getArticleByTime";
        Object articleCache = redisService.get(key);
        if (articleCache == null){
            articleDTOS = new ArrayList<>();
            QueryWrapper<Article> queryWrapper;
            QueryWrapper<Article> queryWrapperByYear;
            for (int i = 2021;i <= 2022;i++){
                queryWrapperByYear = new QueryWrapper<>();
                int yearTemp = i+1;
                queryWrapperByYear.between("article_date",i+"-01-01 00:00:00",yearTemp+"-01-01 00:00:00");
                List<Article> articleList = articleMapper.selectList(queryWrapperByYear);
                ArticleDTO articleDTO = new ArticleDTO();
                List<Map<Integer,List<Article>>> monthList = new ArrayList<>();
                for (int j = 1;j <= 11;j++){
                    queryWrapper = new QueryWrapper<>();
                    int monthTemp = j+1;
                    queryWrapper.between("article_date",i+"-"+j+"-01 00:00:00",i+"-"+monthTemp+"-01 00:00:00");
                    List<Article> articles = articleMapper.selectList(queryWrapper);
                    if (articles != null && articles.size() > 0){
                        HashMap<Integer, List<Article>> hashMap = new HashMap<>();
                        hashMap.put(j,articles);
                        monthList.add(hashMap);
                    }
                }
                queryWrapper = new QueryWrapper<>();
                queryWrapper.between("article_date",i+"-12-01 00:00:00",yearTemp+"-01-01 00:00:00");
                List<Article> articles = articleMapper.selectList(queryWrapper);
                if (articles != null && articles.size() > 0){
                    HashMap<Integer, List<Article>> hashMap = new HashMap<>();
                    hashMap.put(12,articles);
                    monthList.add(hashMap);
                }
                articleDTO.setYear(i);
                articleDTO.setArticlesByYear(articleList);
                articleDTO.setMonth(monthList);
                articleDTOS.add(articleDTO);
                redisService.set(key,articleDTOS);
            }
        }else {
            articleDTOS = (List<ArticleDTO>)articleCache;
        }
        return articleDTOS;
    }
    //随便看看模块根据时间查询最新的三篇文章,以及轮播图文章信息
    @Override
    public List<Article> getArticleToHavingLooking() {
        String key = "getArticleToHavingLooking";
        Object redisCache = redisService.get(key);
        if (redisCache == null){
            QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("id");
            queryWrapper.last("limit 3");
            List<Article> articles = articleMapper.selectList(queryWrapper);
            redisService.set(key,articles);
            return articles;
        }else {
            List<Article> articles;
            articles = (List<Article>) redisCache;
            return articles;
        }
    }

    //点击文章后阅读量增加
    @Override
    public void ReadingNumAdd(int id) {
        Article article = articleMapper.selectById(id);
        Integer articleView = article.getArticleView();
        articleView+=1;
        article.setArticleView(articleView);
        articleMapper.updateById(article);
    }

}
