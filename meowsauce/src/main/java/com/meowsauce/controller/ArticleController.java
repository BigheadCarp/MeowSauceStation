package com.meowsauce.controller;

import com.meowsauce.entity.Article;
import com.meowsauce.result.Result;
import com.meowsauce.result.ResultFactory;
import com.meowsauce.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //后台保存文章
    @PostMapping("/api/admin/content/article")
    public Result saveArticle(@RequestBody Article article){
        articleService.addOrUpdate(article);
        return ResultFactory.buildSuccessResult("保存成功");
    }
    //展示文章-分页
    @GetMapping("/api/article/{size}/{page}")
    public Result listArticles(@PathVariable("size") int size,@PathVariable("page") int page){
        return ResultFactory.buildSuccessResult(articleService.list(page-1,size));
    }

    //根据文章id查询单个文章
    @GetMapping("/api/article/{id}")
    public Result getOneArticle(@PathVariable("id") int id){
        return ResultFactory.buildSuccessResult(articleService.findById(id));
    }
    //后台根据id删除文章
    @DeleteMapping("/api/admin/content/article/{id}")
    public Result deleteArticle(@PathVariable("id") int id){
        articleService.delete(id);
        return ResultFactory.buildSuccessResult("删除成功");
    }
    //后台查询所有文章
    @GetMapping("/api/admin/getAllArticle")
    public Result getAllArticle(){
        List<Article> allArticleName = articleService.getAllArticle();
        return ResultFactory.buildSuccessResult(allArticleName);
    }

    //搜索页,根据文章标题查询文章并分页
    @GetMapping("/api/search/{size}/{page}/{articleTitle}")
    public Result getArticleByArticleName(@PathVariable("size") int size,
                                          @PathVariable("page") int page,
                                          @PathVariable("articleTitle") String articleTitle){
       return ResultFactory.buildSuccessResult(articleService.listByArticleTitle(page-1,size,articleTitle));
    }
    //分类页,根据分类项查询文章并分页
    @GetMapping("/api/type/{size}/{page}/{articleType}")
    public Result getArticleByArticleType(@PathVariable("size") int size,
                                          @PathVariable("page") int page,
                                          @PathVariable("articleType") String articleType){
        return ResultFactory.buildSuccessResult(articleService.listByArticleType(page-1,size,articleType));
    }

    //查询分类文章数
    @GetMapping("/api/type/{articleType}")
    public Result getArticleTotalByType(@PathVariable("articleType") String articleType){
        return ResultFactory.buildSuccessResult(articleService.getArticleTotalByType(articleType));
    }
    //查询搜索文章数
    @GetMapping("/api/search/{articleTitle}")
    public Result getArticleTotalByArticleTitle(@PathVariable("articleTitle") String articleTitle){
        return ResultFactory.buildSuccessResult(articleService.getArticleTotalByArticleTitle(articleTitle));
    }
    //随便看看组件查询最新的三篇文章
    @CrossOrigin
    @GetMapping("/api/HavingLooking")
    public Result getArticleToHavingLooking(){
        return ResultFactory.buildSuccessResult(articleService.getArticleToHavingLooking());
    }
    //查看文章阅读量加一
    @GetMapping("/api/ReadingNumAdd/{id}")
    public Result ReadingNumAdd(@PathVariable("id") int id){
        articleService.ReadingNumAdd(id);
        return ResultFactory.buildSuccessResult("成功");
    }
}
